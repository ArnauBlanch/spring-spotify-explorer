package xyz.arnau.spotifyexplorer.integration;

import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class TrackControllerTest {
    @LocalServerPort
    private Integer port;

    @Rule
    public WireMockRule mockSpotifyRule = new WireMockRule(wireMockConfig()
            .port(8077)
            .extensions(new ResponseTemplateTransformer(true)));

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";

        mockSpotifyRule.stubFor(get(urlPathMatching("/v1/search"))
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
    public void addTrackToAppPlaylist() throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(new JSONObject().put("name", "test name").toString())
        .when()
                .post("/track")
        .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @Ignore
    public void notAddTrackToAppPlaylistIfTrackIsNotFound() throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(new JSONObject().put("name", "unexisting track").toString())
        .when()
                .post("/track")
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
