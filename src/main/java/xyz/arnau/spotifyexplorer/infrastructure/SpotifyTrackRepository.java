package xyz.arnau.spotifyexplorer.infrastructure;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import xyz.arnau.spotifyexplorer.domain.Track;
import xyz.arnau.spotifyexplorer.domain.TrackRepository;

public class SpotifyTrackRepository implements TrackRepository {
    private RestTemplate restTemplate;

    public SpotifyTrackRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Track findByName(String name) {
        ResponseEntity<SpotifySearchTrackResponse> response =
                restTemplate.getForEntity("http://localhost:8077/v1/search", SpotifySearchTrackResponse.class);
        SpotifyTrackResponse trackResponse = response.getBody().getTracks().getItems().get(0);

        Track track = new Track(trackResponse.getId(), trackResponse.getName(), trackResponse.getArtists().get(0).getName());
        return track;
    }
}
