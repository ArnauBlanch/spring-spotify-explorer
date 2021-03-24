package xyz.arnau.spotifyexplorer.application;

import org.springframework.stereotype.Service;
import xyz.arnau.spotifyexplorer.domain.PlayListRepository;
import xyz.arnau.spotifyexplorer.domain.Track;
import xyz.arnau.spotifyexplorer.domain.TrackNotFoundException;
import xyz.arnau.spotifyexplorer.domain.TrackRepository;

public class SaveTrack {
    private final TrackRepository trackRepository;
    private final PlayListRepository playListRepository;

    public SaveTrack(TrackRepository trackRepository, PlayListRepository playListRepository) {
        this.trackRepository = trackRepository;
        this.playListRepository = playListRepository;
    }

    public void execute(String trackName) throws TrackNotFoundException {
        Track track = trackRepository.findByName(trackName);
        if (track == null)
            throw new TrackNotFoundException();

        playListRepository.add(track);
    }
}
