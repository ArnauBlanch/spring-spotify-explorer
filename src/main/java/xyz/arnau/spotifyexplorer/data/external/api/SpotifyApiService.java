package xyz.arnau.spotifyexplorer.data.external.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import xyz.arnau.spotifyexplorer.data.external.model.SpotifyUser;

public interface SpotifyApiService {
    @GET("/v1/me")
    Call<SpotifyUser> getCurrentUserProfile(@Header("Authorization") String authHeader);
}
