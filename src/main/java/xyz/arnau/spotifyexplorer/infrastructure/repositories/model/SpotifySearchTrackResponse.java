package xyz.arnau.spotifyexplorer.infrastructure.repositories.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpotifySearchTrackResponse {
    public SpotifyPagingResponse getTracks() {
        return tracks;
    }

    @JsonProperty
    private SpotifyPagingResponse tracks;
}
