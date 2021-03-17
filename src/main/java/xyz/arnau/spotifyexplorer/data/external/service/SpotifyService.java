package xyz.arnau.spotifyexplorer.data.external.service;

import org.springframework.stereotype.Service;
import xyz.arnau.spotifyexplorer.data.external.api.SpotifyApiService;
import xyz.arnau.spotifyexplorer.data.external.api.SpotifyApiServiceGenerator;
import xyz.arnau.spotifyexplorer.data.external.model.SpotifyUser;

@Service
public class SpotifyService {
    private final SpotifyApiService apiService;

    public SpotifyService(SpotifyApiServiceGenerator apiServiceGenerator) {
        this.apiService = apiServiceGenerator.createApiService();
    }

    public SpotifyUser getCurrentUserProfile(String authToken) {
        try {
            var response = this.apiService.getCurrentUserProfile("Bearer " + authToken).execute();
            return response.isSuccessful() ? response.body() : null;
        } catch (Exception e) {
            System.out.println("Could not get current user profile");
            e.printStackTrace();
            return null;
        }
    }
}
