package xyz.arnau.spotifyexplorer.data.external.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import xyz.arnau.spotifyexplorer.data.external.model.ApiToken;

public interface SpotifyAccountApiService {
    @FormUrlEncoded
    @POST("/api/token")
    Call<ApiToken> getToken(@Field("grant_type") String grantType, @Field("code") String code,
                                   @Field("redirect_uri") String redirectUri,
                                   @Header("Authorization") String authorizationHeader);
}
