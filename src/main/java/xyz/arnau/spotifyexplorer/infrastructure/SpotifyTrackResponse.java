package xyz.arnau.spotifyexplorer.infrastructure;

import java.util.List;

public class SpotifyTrackResponse {
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private String id;
    private String name;

    public List<SpotifyArtistResponse> getArtists() {
        return artists;
    }

    private List<SpotifyArtistResponse> artists;
}
