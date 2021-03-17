package xyz.arnau.spotifyexplorer.services;

import org.springframework.stereotype.Service;
import xyz.arnau.spotifyexplorer.data.external.helper.SpotifyAuthHelper;
import xyz.arnau.spotifyexplorer.data.external.service.SpotifyAuthService;

@Service
public class AuthService {
    private final SpotifyAuthHelper spotifyAuthHelper;
    private final SpotifyAuthService spotifyAuthService;

    public AuthService(SpotifyAuthHelper spotifyAuthHelper, SpotifyAuthService spotifyAuthService) {
        this.spotifyAuthHelper = spotifyAuthHelper;
        this.spotifyAuthService = spotifyAuthService;
    }

    public String getAuthorizeUrl() {
        return this.spotifyAuthHelper.getAuthorizeUrl();
    }

    public void saveUserToken(String authorizationCode) {
        var token = this.spotifyAuthService.getToken(authorizationCode);
    }
}
