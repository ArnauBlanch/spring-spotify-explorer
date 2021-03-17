package xyz.arnau.spotifyexplorer.data.external.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.arnau.spotifyexplorer.data.external.config.SpotifyApiConfiguration;

@Service
public class SpotifyApiServiceGenerator {
    private final SpotifyApiConfiguration apiConfiguration;

    public SpotifyApiServiceGenerator(SpotifyApiConfiguration apiConfiguration) {
        this.apiConfiguration = apiConfiguration;
    }

    private Retrofit createRetrofit() {
        var gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        return new Retrofit.Builder()
                .baseUrl(apiConfiguration.getApiUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(new OkHttpClient.Builder().build())
                .build();
    }

    public <S> S createService(Class<S> serviceClass) {
        return createRetrofit().create(serviceClass);
    }
}
