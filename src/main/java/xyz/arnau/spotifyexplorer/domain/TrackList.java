package xyz.arnau.spotifyexplorer.domain;

import java.util.List;

public class TrackList {
    private final List<Track> tracks;

    public TrackList(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Track> getTracks() {
        return tracks;
    }
}
