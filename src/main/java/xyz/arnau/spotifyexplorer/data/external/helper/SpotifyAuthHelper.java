package xyz.arnau.spotifyexplorer.data.external.helper;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import xyz.arnau.spotifyexplorer.data.external.config.SpotifyApiConfiguration;

@Service
public class SpotifyAuthHelper {
    private SpotifyApiConfiguration apiConfiguration;

    public SpotifyAuthHelper(SpotifyApiConfiguration apiConfiguration) {
        this.apiConfiguration = apiConfiguration;
    }

    public String getAuthorizeUrl() {
        var uriBuilder = UriComponentsBuilder.fromHttpUrl(apiConfiguration.getUserPermissionUrl())
                .queryParam("response_type", "code")
                .queryParam("client_id", apiConfiguration.getCredentials().getClientId())
                .queryParam("scope", apiConfiguration.getScopes())
                .queryParam("redirect_uri", apiConfiguration.getCallbackUrl());

        return uriBuilder.toUriString();
    }
}
