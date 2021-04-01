package xyz.arnau.spotifyexplorer.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SpotifyTrackResponse {
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    public List<SpotifyArtistResponse> getArtists() {
        return artists;
    }

    @JsonProperty
    private List<SpotifyArtistResponse> artists;
}
