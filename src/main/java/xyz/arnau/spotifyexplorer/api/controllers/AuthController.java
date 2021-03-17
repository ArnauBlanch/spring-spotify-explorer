package xyz.arnau.spotifyexplorer.api.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import xyz.arnau.spotifyexplorer.data.external.helper.SpotifyAuthHelper;
import xyz.arnau.spotifyexplorer.data.external.service.SpotifyAuthService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final SpotifyAuthService authService;
    private final SpotifyAuthHelper authHelper;

    private static final String COOKIE_NAME = "spotify-token";

    public AuthController(SpotifyAuthService authService, SpotifyAuthHelper authHelper) {
        this.authService = authService;
        this.authHelper = authHelper;
    }

    @GetMapping("/login")
    public View authorizeUser() {
        var authorizeUrl = this.authHelper.getAuthorizeUrl();
        return new RedirectView(authorizeUrl);
    }

    @GetMapping("/logout")
    public String logOut(HttpServletResponse response) {
        var cookie = new Cookie(COOKIE_NAME, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "Logged out!";
    }

    @GetMapping("/callback")
    public String authCodeCallback(@RequestParam String code, HttpServletResponse response) {
        var token = authService.getToken(code);
        var cookie = new Cookie("spotify-token", token.getAccessToken());
        cookie.setPath("/");
        cookie.setMaxAge(token.getExpiresIn());
        response.addCookie(cookie);
        return "Logged in!";
    }

    @GetMapping("/cookie-test")
    public String cookieTest(@CookieValue String test) {
        return "Cookie=" + test;
    }
}
