package xyz.arnau.spotifyexplorer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestTemplate;
import xyz.arnau.spotifyexplorer.application.SaveTrack;
import xyz.arnau.spotifyexplorer.domain.PlayListRepository;
import xyz.arnau.spotifyexplorer.domain.TrackRepository;
import xyz.arnau.spotifyexplorer.infrastructure.repositories.SpotifyApiConfig;
import xyz.arnau.spotifyexplorer.infrastructure.repositories.SpotifyAuthService;
import xyz.arnau.spotifyexplorer.infrastructure.repositories.SpotifyTrackRepository;
import xyz.arnau.spotifyexplorer.infrastructure.repositories.SqlPlayListRepository;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
public class SpotifyExplorerConfig {
    @Autowired
    Environment env;

    @Bean
    public SaveTrack saveTrack(TrackRepository trackRepository, PlayListRepository playListRepository) {
        return new SaveTrack(trackRepository, playListRepository);
    }

    @Bean
    public TrackRepository trackRepository(RestTemplate restTemplate, SpotifyAuthService spotifyAuthService, SpotifyApiConfig apiConfig) {
        return new SpotifyTrackRepository(restTemplate, spotifyAuthService, apiConfig);
    }

    @Bean
    public SpotifyAuthService spotifyAuthService(RestTemplate restTemplate, SpotifyApiConfig spotifyApiConfig) {
        return new SpotifyAuthService(restTemplate, spotifyApiConfig);
    }

    @Bean
    public PlayListRepository playListRepository(JdbcTemplate jdbcTemplate) {
        return new SqlPlayListRepository(jdbcTemplate);
    }

    @Bean
    public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("jdbc.driver-class-name")));
        dataSource.setUrl(Objects.requireNonNull(env.getProperty("jdbc.url")));
        dataSource.setUsername(Objects.requireNonNull(env.getProperty("jdbc.user")));
        dataSource.setPassword(Objects.requireNonNull(env.getProperty("jdbc.pass")));

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
