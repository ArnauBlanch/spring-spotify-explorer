package xyz.arnau.spotifyexplorer.integration;

import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import xyz.arnau.spotifyexplorer.domain.Track;
import xyz.arnau.spotifyexplorer.domain.TrackRepository;
import xyz.arnau.spotifyexplorer.infrastructure.repositories.SpotifyApiConfig;
import xyz.arnau.spotifyexplorer.infrastructure.repositories.SpotifyAuthService;
import xyz.arnau.spotifyexplorer.infrastructure.repositories.SpotifyTrackRepository;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SpotifyTrackRepositoryTest {
    private TrackRepository trackRepository;

    @Rule
    public WireMockRule mockSpotifyApiRule = new WireMockRule(wireMockConfig()
            .port(8077)
            .extensions(new ResponseTemplateTransformer(true)));

    @Rule
    public WireMockRule mockSpotifyAccountsRule = new WireMockRule(wireMockConfig()
            .port(8078)
            .extensions(new ResponseTemplateTransformer(true)));
    @Before
    public void setUp() throws Exception {
        SpotifyApiConfig apiConfig = new SpotifyApiConfig();
        apiConfig.setApiUrl("http://localhost:8077");
        apiConfig.setAccountsUrl("http://localhost:8078");

        trackRepository = new SpotifyTrackRepository(new RestTemplate(), new SpotifyAuthService(new RestTemplate(), apiConfig), apiConfig);

        mockSpotifyAccountsRule.stubFor(get(urlPathMatching("/api/token"))
                .willReturn(okJson("{\n" +
                        "    \"access_token\": \"TEST_TOKEN\",\n" +
                        "    \"token_type\": \"Bearer\",\n" +
                        "    \"expires_in\": 3600\n" +
                        "}")));

        mockSpotifyApiRule.stubFor(get(urlPathMatching("/v1/search"))
                .withHeader("Authorization", matching("Bearer TEST_TOKEN"))
                .willReturn(okJson("{\n" +
                        "    \"tracks\": {\n" +
                        "        \"items\": [\n" +
                        "            {\n" +
                        "                \"id\": \"testId\",\n" +
                        "                \"name\": \"testName\",\n" +
                        "                \"type\": \"track\",\n" +
                        "                \"uri\": \"spotify:artist:08td7MxkoHQkXnWAYD8d6Q\",\n" +
                        "                \"artists\": [{ \"name\": \"testSinger\" }]\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"limit\": 20,\n" +
                        "        \"next\": null,\n" +
                        "        \"offset\": 0,\n" +
                        "        \"previous\": null,\n" +
                        "        \"total\": 1\n" +
                        "    }\n" +
                        "}")));
    }

    @Test
    public void findsTrackByName() {
        String trackName = "testName";
        Track track = trackRepository.findByName(trackName);

        assertThat(track, is(equalTo(new Track("testId", trackName, "testSinger"))));
    }
}
