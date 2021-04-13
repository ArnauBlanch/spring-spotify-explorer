package xyz.arnau.spotifyexplorer.integration;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import xyz.arnau.spotifyexplorer.domain.PlayListRepository;
import xyz.arnau.spotifyexplorer.domain.Track;
import xyz.arnau.spotifyexplorer.infrastructure.repositories.SqlPlayListRepository;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SqlPlayListRepositoryTest {
    @Test
    public void addTheTrackToThePlayList() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql").build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        PlayListRepository playListRepository = new SqlPlayListRepository(jdbcTemplate);

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
}
