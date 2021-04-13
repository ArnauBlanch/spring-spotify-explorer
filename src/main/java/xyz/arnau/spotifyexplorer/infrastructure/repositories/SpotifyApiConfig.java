package xyz.arnau.spotifyexplorer.infrastructure.repositories;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spotify")
public class SpotifyApiConfig {
    private String apiUrl;
    private String accountsUrl;

    public String getAccountsUrl() {
        return accountsUrl;
    }

    public void setAccountsUrl(String accountsUrl) {
        this.accountsUrl = accountsUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
}
