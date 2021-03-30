package xyz.arnau.spotifyexplorer.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class TrackControllerTest {
    @LocalServerPort
    private Integer port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
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
