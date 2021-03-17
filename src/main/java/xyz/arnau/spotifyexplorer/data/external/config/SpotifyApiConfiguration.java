package xyz.arnau.spotifyexplorer.data.external.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import xyz.arnau.spotifyexplorer.data.external.config.dto.SpotifyCredentials;

@Configuration
@ConfigurationProperties(prefix = "spotify")
public class SpotifyApiConfiguration {
    private SpotifyCredentials credentials;
    private String userPermissionUrl;
    private String apiUrl;
    private String callbackUrl;

    private String scopes;

    public SpotifyCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(SpotifyCredentials credentials) {
        this.credentials = credentials;
    }

    public String getUserPermissionUrl() {
        return userPermissionUrl;
    }

    public void setUserPermissionUrl(String userPermissionUrl) {
        this.userPermissionUrl = userPermissionUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}