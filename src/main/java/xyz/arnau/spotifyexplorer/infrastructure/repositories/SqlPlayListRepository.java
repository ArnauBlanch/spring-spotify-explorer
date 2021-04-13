package xyz.arnau.spotifyexplorer.infrastructure.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import xyz.arnau.spotifyexplorer.domain.PlayListRepository;
import xyz.arnau.spotifyexplorer.domain.Track;

public class SqlPlayListRepository implements PlayListRepository {
    private JdbcTemplate jdbcTemplate;

    public SqlPlayListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Track track) {
        String sql = "INSERT INTO PlayList (id, name, singer) "
                + "VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, new Object[] {
                track.getId(), track.getName(), track.getSinger() });

    }
}
