package xyz.arnau.spotifyexplorer.integration;

import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.arnau.spotifyexplorer.domain.Track;
import xyz.arnau.spotifyexplorer.domain.TrackList;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
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
                .withQueryParam("type", matching("track"))
                .withQueryParam("q", matching("name:test name"))
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

        mockSpotifyRule.stubFor(get(urlPathMatching("/v1/search"))
                .withQueryParam("type", matching("track"))
                .withQueryParam("q", matching("name:unexisting track"))
                .willReturn(notFound()));
    }

    @Test
    public void addTrackToAppPlaylist() throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(new JSONObject().put("name", "test name").toString())
        .when()
                .post("/tracks/create")
        .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void notAddTrackToAppPlaylistIfTrackIsNotFound() throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(new JSONObject().put("name", "unexisting track").toString())
        .when()
                .post("/tracks/create")
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void searchTrackFromPlaylist() {
        TrackList expectedTracks = new TrackList(List.of(new Track("testId", "testName", "testSinger")));
        given()
                .contentType(ContentType.JSON)
                .queryParam("keyword", "food")
        .when()
                .get("/tracks/search")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("tracks", hasItems(expectedTracks));
    }
}
