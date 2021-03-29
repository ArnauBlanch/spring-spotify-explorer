package xyz.arnau.spotifyexplorer.integration;

import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MeControllerShould {
    @LocalServerPort
    private Integer port;

    @Autowired MockMvc mockMvc;

    private WebDriver webDriver;

    @Rule
    public WireMockRule mockOAuth2Provider = new WireMockRule(wireMockConfig()
            .port(8077)
            .extensions(new ResponseTemplateTransformer(true)));

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);

        webDriver = new ChromeDriver(options);

//        mockOAuth2Provider.stubFor(get(urlPathEqualTo("/favicon.ico")).willReturn(notFound()));

        mockOAuth2Provider.stubFor(get(urlPathMatching("/oauth/authorize?.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/html")
                        .withBodyFile("login.html")));

        mockOAuth2Provider.stubFor(post(urlPathEqualTo("/login"))
                .willReturn(temporaryRedirect("{{formData request.body 'form' urlDecode=true}}{{{form.redirectUri}}}?code={{{randomValue length=30 type='ALPHANUMERIC'}}}&state={{{form.state}}}")));

        mockOAuth2Provider.stubFor(post(urlPathEqualTo("/oauth/token"))
                .willReturn(okJson("{\"token_type\": \"Bearer\",\"access_token\":\"{{randomValue length=20 type='ALPHANUMERIC'}}\"}")));

        mockOAuth2Provider.stubFor(get(urlPathEqualTo("/userinfo"))
                .willReturn(okJson("{\"sub\":\"my-id\",\"email\":\"bwatkins@test.com\"}")));

        mockOAuth2Provider.stubFor(get(urlPathEqualTo("/v1/me"))
            .willReturn(okJson("{\"sub\":\"my-id\",\"email\":\"bwatkins@test.com\"}")));
    }

    @Test
    public void logs_in_via_wiremock_sso_and_retrieve_me_information() throws Exception {
        webDriver.get("http://localhost:" + port + "/oauth2/authorization/wiremock");

        webDriver.findElement(By.name("username")).sendKeys("bwatkins@test.com");
        webDriver.findElement(By.name("password")).sendKeys("pass123");
        webDriver.findElement(By.id("submit")).click();

        OAuth2AuthenticationToken principal = buildPrincipal();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
            new SecurityContextImpl(principal));

        mockMvc.perform(MockMvcRequestBuilders.get("/me")
            .session(session)
            .contentType(MediaType.APPLICATION_JSON)
            .content("test"))
            .andExpect(status().isOk());
    }

    private static OAuth2AuthenticationToken buildPrincipal() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", "my-id");
        attributes.put("email", "bwatkins@test.org");

        List<GrantedAuthority> authorities = Collections.singletonList(
            new OAuth2UserAuthority("ROLE_USER", attributes));
        OAuth2User user = new DefaultOAuth2User(authorities, attributes, "sub");
        return new OAuth2AuthenticationToken(user, authorities, "wiremock");
    }
}
