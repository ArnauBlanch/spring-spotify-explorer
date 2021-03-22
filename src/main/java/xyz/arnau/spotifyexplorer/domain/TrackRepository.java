package xyz.arnau.spotifyexplorer.domain;

public interface TrackRepository {
    Track findByName(String name);
}
