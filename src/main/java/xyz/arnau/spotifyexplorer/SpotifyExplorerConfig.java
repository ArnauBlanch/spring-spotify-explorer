package xyz.arnau.spotifyexplorer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.arnau.spotifyexplorer.application.SaveTrack;
import xyz.arnau.spotifyexplorer.domain.PlayListRepository;
import xyz.arnau.spotifyexplorer.domain.TrackRepository;

@Configuration
public class SpotifyExplorerConfig {
    @Bean
    public SaveTrack saveTrack(TrackRepository trackRepository, PlayListRepository playListRepository) {
        return new SaveTrack(trackRepository, playListRepository);
    }
}
