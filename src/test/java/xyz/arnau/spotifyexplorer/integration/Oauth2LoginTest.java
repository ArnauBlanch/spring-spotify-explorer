package xyz.arnau.spotifyexplorer.integration;

import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
public class Oauth2LoginTest {
    @LocalServerPort
    private Integer port;

    @Rule
    public WireMockRule mockOAuth2Provider = new WireMockRule(wireMockConfig()
            .port(8077)
            .extensions(new ResponseTemplateTransformer(true)));

    @Before
    public void setUp() {
        mockOAuth2Provider.stubFor(get(urlPathMatching("/oauth/authorize"))
                .willReturn(okJson("{ redirectUri: \"http://localhost:8077/oauth/login\"}")));

        mockOAuth2Provider.stubFor(post(urlPathEqualTo("/login"))
                .willReturn(temporaryRedirect("{{formData request.body 'form' urlDecode=true}}{{{form.redirectUri}}}?code={{{randomValue length=30 type='ALPHANUMERIC'}}}&state={{{form.state}}}")));

        mockOAuth2Provider.stubFor(post(urlPathEqualTo("/oauth/token"))
                .willReturn(okJson("{\"token_type\": \"Bearer\",\"access_token\":\"{{randomValue length=20 type='ALPHANUMERIC'}}\"}")));

        mockOAuth2Provider.stubFor(get(urlPathEqualTo("/me"))
                .willReturn(okJson("{\"sub\":\"my-id\",\"email\":\"bwatkins@test.com\"}")));
    }

    @Test
    public void logs_in_via_wiremock_sso() throws Exception {
        given()
                .port(port)
        .when()
                .get("/oauth2/authorization/wiremock")
        .then()
                .body(equalTo("{ redirectUri: \"http://localhost:8077/oauth/login\"}"));
    }

}
