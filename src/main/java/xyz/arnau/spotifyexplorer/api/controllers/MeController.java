package xyz.arnau.spotifyexplorer.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import xyz.arnau.spotifyexplorer.data.external.service.SpotifyService;

@RestController
public class MeController {
    private final SpotifyService spotifyService;

    public MeController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }
/*
    @GetMapping("/me")
    public SpotifyUser getCurrentUserProfile(@RegisteredOAuth2AuthorizedClient("spotify") OAuth2AuthorizedClient authorizedClient) {
        return this.spotifyService.getCurrentUserProfile(authorizedClient.getAccessToken().getTokenValue());
    }*/
}
