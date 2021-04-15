package xyz.arnau.spotifyexplorer.integration;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import xyz.arnau.spotifyexplorer.domain.PlayListRepository;
import xyz.arnau.spotifyexplorer.domain.Track;
import xyz.arnau.spotifyexplorer.domain.TrackList;
import xyz.arnau.spotifyexplorer.infrastructure.repositories.SqlPlayListRepository;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class SqlPlayListRepositoryTest {
    JdbcTemplate jdbcTemplate;
    PlayListRepository playListRepository;

    @Before
    public void setUp(){
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql").build();
        jdbcTemplate = new JdbcTemplate(dataSource);
        playListRepository = new SqlPlayListRepository(jdbcTemplate);
    }

    @Test
    public void addTheTrackToThePlayList() {
        Track expectedTrack = new Track("an-id", "a-track-name", "a-singer");
        playListRepository.add(expectedTrack);

        String sql = "SELECT * FROM PlayList WHERE id=?;";

        Track track = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new Track(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("singer")
                ), "an-id");

        assertThat(track, is(equalTo(expectedTrack)));
    }

    @Test
    public void searchTracksOfThePlayList() {
        String keyword = "a-track-name";

        Track expectedTrack = new Track("id1", "a-track-name", "a-singer");
        playListRepository.add(expectedTrack);

        TrackList expectedTrackList = new TrackList(List.of(expectedTrack));
        TrackList trackList = playListRepository.search(keyword);

        assertEquals(trackList.getTracks(), expectedTrackList.getTracks());
    }

    @Test
    public void searchTracksOfThePlayListNoResults() {
        String keyword = "not-a-track-name";

        Track expectedTrack = new Track("id2", "a-track-name", "a-singer");
        playListRepository.add(expectedTrack);

        TrackList expectedTrackList = new TrackList(new ArrayList<>());
        TrackList trackList = playListRepository.search(keyword);

        assertEquals(trackList.getTracks(), expectedTrackList.getTracks());
    }
}
