package xyz.arnau.spotifyexplorer.integration;

import org.junit.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import xyz.arnau.spotifyexplorer.domain.PlayListRepository;
import xyz.arnau.spotifyexplorer.domain.Track;
import xyz.arnau.spotifyexplorer.infrastructure.SqlPlayListRepository;

import javax.sql.DataSource;

public class SqlPlayListRepositoryTest {
    @Test
    public void addTheTrackToThePlayList() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:jdbc/schema.sql").build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        PlayListRepository playListRepository = new SqlPlayListRepository(jdbcTemplate);

        playListRepository.add(new Track("an-id", "a-track-name", "a-singer"));


    }
}
