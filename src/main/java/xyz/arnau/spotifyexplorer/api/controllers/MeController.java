package xyz.arnau.spotifyexplorer.api.controllers;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.arnau.spotifyexplorer.data.external.model.SpotifyUser;
import xyz.arnau.spotifyexplorer.data.external.service.SpotifyService;

@RestController
public class MeController {
    private final SpotifyService spotifyService;

    public MeController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @GetMapping("/me")
    public SpotifyUser getCurrentUserProfile(@CookieValue("spotify-token")String spotifyToken) {
        return this.spotifyService.getCurrentUserProfile(spotifyToken);
    }
}
