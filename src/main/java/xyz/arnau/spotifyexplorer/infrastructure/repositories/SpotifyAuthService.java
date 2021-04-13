package xyz.arnau.spotifyexplorer.infrastructure.repositories;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import xyz.arnau.spotifyexplorer.infrastructure.repositories.model.SpotifyTokenResponse;

public class SpotifyAuthService {
    private RestTemplate restTemplate;
    private SpotifyApiConfig spotifyApiConfig;

    public SpotifyAuthService(RestTemplate restTemplate, SpotifyApiConfig spotifyApiConfig) {
        this.restTemplate = restTemplate;
        this.spotifyApiConfig = spotifyApiConfig;
    }
    public String getToken() {
        try {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(spotifyApiConfig.getAccountsUrl() + "/api/token");
            ResponseEntity<SpotifyTokenResponse> response = restTemplate.getForEntity(uriBuilder.toUriString(), SpotifyTokenResponse.class);
            SpotifyTokenResponse responseBody = response.getBody();

            return responseBody.getTokenType() + " " + responseBody.getAccessToken();
        } catch (RestClientException e) {
            return null;
        }
    }

}
