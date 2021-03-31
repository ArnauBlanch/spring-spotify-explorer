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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@JdbcTest
@Sql({"schema.sql"})
public class TrackControllerTest {
    @LocalServerPort
    private Integer port;

    @Autowired
    private DataSource dataSource;


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

        ClassPathResource file = new ClassPathResource("schema.sql");
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(file);
        resourceDatabasePopulator.execute(dataSource);
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
