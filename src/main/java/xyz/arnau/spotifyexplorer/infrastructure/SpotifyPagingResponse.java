package xyz.arnau.spotifyexplorer.infrastructure;

import java.util.List;

public class SpotifyPagingResponse {
    public List<SpotifyTrackResponse> getItems() {
        return items;
    }

    private List<SpotifyTrackResponse> items;
}
