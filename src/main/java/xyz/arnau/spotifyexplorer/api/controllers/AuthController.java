package xyz.arnau.spotifyexplorer.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import xyz.arnau.spotifyexplorer.services.AuthService;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public View authorizeUser() {
        var authorizeUrl = this.authService.getAuthorizeUrl();
        return new RedirectView(authorizeUrl);
    }

    @GetMapping("/callback")
    public String authCodeCallback(@RequestParam String code) {
        authService.saveUserToken(code);
        return "Token saved!";
    }
}
