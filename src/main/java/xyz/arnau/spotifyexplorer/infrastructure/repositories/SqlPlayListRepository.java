package xyz.arnau.spotifyexplorer.infrastructure.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import xyz.arnau.spotifyexplorer.domain.PlayListRepository;
import xyz.arnau.spotifyexplorer.domain.Track;
import xyz.arnau.spotifyexplorer.domain.TrackList;

import java.util.List;

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

    @Override
    public TrackList search(String keyword) {
        String sql = "SELECT * FROM PlayList WHERE name = ?";
        List<Track> listOfTracks = jdbcTemplate.query(sql, (rs, rowNum) ->
                new Track(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("singer")
                ), keyword);
        return new TrackList(listOfTracks);
    }
}
