package xyz.arnau.spotifyexplorer.infrastructure;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import xyz.arnau.spotifyexplorer.domain.Track;
import xyz.arnau.spotifyexplorer.domain.TrackRepository;

public class SpotifyTrackRepository implements TrackRepository {
    private RestTemplate restTemplate;

    public SpotifyTrackRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Track findByName(String name) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8077/v1/search")
                .queryParam("type", "track")
                .queryParam("q", "name:" + name);
        ResponseEntity<SpotifySearchTrackResponse> response =
                restTemplate.getForEntity(uriBuilder.build().toUriString(), SpotifySearchTrackResponse.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            return null;
        }

        SpotifyTrackResponse trackResponse = response.getBody().getTracks().getItems().get(0);

        return new Track(trackResponse.getId(), trackResponse.getName(), trackResponse.getArtists().get(0).getName());
    }
}
