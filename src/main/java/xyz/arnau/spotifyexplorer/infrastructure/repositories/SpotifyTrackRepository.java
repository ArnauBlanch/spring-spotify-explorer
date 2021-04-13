package xyz.arnau.spotifyexplorer.infrastructure.repositories;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import xyz.arnau.spotifyexplorer.domain.Track;
import xyz.arnau.spotifyexplorer.domain.TrackRepository;
import xyz.arnau.spotifyexplorer.infrastructure.repositories.model.SpotifySearchTrackResponse;
import xyz.arnau.spotifyexplorer.infrastructure.repositories.model.SpotifyTrackResponse;

public class SpotifyTrackRepository implements TrackRepository {
    private RestTemplate restTemplate;
    private SpotifyAuthService spotifyAuthService;
    private SpotifyApiConfig spotifyApiConfig;

    public SpotifyTrackRepository(RestTemplate restTemplate, SpotifyAuthService spotifyAuthService, SpotifyApiConfig spotifyApiConfig) {
        this.restTemplate = restTemplate;
        this.spotifyAuthService = spotifyAuthService;
        this.spotifyApiConfig = spotifyApiConfig;
    }

    @Override
    public Track findByName(String name) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(spotifyApiConfig.getApiUrl() + "/v1/search")
                .queryParam("type", "track")
                .queryParam("q", "name:" + name);
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Authorization", spotifyAuthService.getToken());
            ResponseEntity<SpotifySearchTrackResponse> response =
                    restTemplate.exchange(uriBuilder.build().toUriString(), HttpMethod.GET, new HttpEntity<Object>(headers), SpotifySearchTrackResponse.class);
            SpotifyTrackResponse trackResponse = response.getBody().getTracks().getItems().get(0);

            return new Track(trackResponse.getId(), trackResponse.getName(), trackResponse.getArtists().get(0).getName());
        } catch (RestClientException e) {
            return null;
        }
    }
}
