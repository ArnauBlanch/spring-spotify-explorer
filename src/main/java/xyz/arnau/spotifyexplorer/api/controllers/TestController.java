package xyz.arnau.spotifyexplorer.api.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.arnau.spotifyexplorer.data.external.model.SpotifyUser;
import xyz.arnau.spotifyexplorer.data.external.service.SpotifyService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    @GetMapping("/test") public Map<String, String> getUserInfo(@AuthenticationPrincipal OAuth2User user) {
        Authentication authentication = SecurityContextHolder.getContext()
            .getAuthentication();

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("email", user.getAttribute("email"));
        userInfo.put("id", user.getAttribute("sub"));

        return userInfo;
    }
}


