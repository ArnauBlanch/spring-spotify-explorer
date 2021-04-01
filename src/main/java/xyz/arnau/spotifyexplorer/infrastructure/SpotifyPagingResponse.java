package xyz.arnau.spotifyexplorer.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SpotifyPagingResponse {
    public List<SpotifyTrackResponse> getItems() {
        return items;
    }

    @JsonProperty
    private List<SpotifyTrackResponse> items;
}
