package xyz.arnau.spotifyexplorer.data.external.service;

import org.springframework.stereotype.Service;
import xyz.arnau.spotifyexplorer.data.external.api.SpotifyApiServiceGenerator;
import xyz.arnau.spotifyexplorer.data.external.api.SpotifyAccountApiService;
import xyz.arnau.spotifyexplorer.data.external.config.SpotifyApiConfiguration;
import xyz.arnau.spotifyexplorer.data.external.model.ApiToken;

import java.util.Base64;

@Service
public class SpotifyAuthService {
    private final SpotifyApiConfiguration apiConfiguration;
    private final SpotifyAccountApiService accountApiService;

    private static final String GRANT_TYPE = "authorization_code";

    public SpotifyAuthService(SpotifyApiConfiguration apiConfiguration, SpotifyApiServiceGenerator apiServiceGenerator) {
        this.apiConfiguration = apiConfiguration;
        this.accountApiService = apiServiceGenerator.createAccountApiService();
    }

    public ApiToken getToken(String authorizationCode) {
        var authorizationHeader = getAuthorizationHeader();
        try {
            var response = this.accountApiService.getToken(GRANT_TYPE, authorizationCode,
                    apiConfiguration.getCallbackUrl(), authorizationHeader)
                    .execute();
            return response.isSuccessful() ? response.body() : null;
        } catch (Exception e) {
            System.out.println("Could not get Spotify token");
            e.printStackTrace();
            return null;
        }
    }

    private String getAuthorizationHeader() {
        var credentials = apiConfiguration.getCredentials();
        var authCredentials = credentials.getClientId() + ":" + credentials.getClientSecret();
        return "Basic " + Base64.getEncoder().encodeToString(authCredentials.getBytes());
    }
}
