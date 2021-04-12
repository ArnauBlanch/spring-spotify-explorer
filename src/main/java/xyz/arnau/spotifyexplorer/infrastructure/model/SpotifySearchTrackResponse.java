package xyz.arnau.spotifyexplorer.infrastructure.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpotifySearchTrackResponse {
    public SpotifyPagingResponse getTracks() {
        return tracks;
    }

    @JsonProperty
    private SpotifyPagingResponse tracks;
}
