package xyz.arnau.spotifyexplorer.application;

import xyz.arnau.spotifyexplorer.domain.*;

public class TrackService {
    private final TrackRepository trackRepository;
    private final PlayListRepository playListRepository;

    public TrackService(TrackRepository trackRepository, PlayListRepository playListRepository) {
        this.trackRepository = trackRepository;
        this.playListRepository = playListRepository;
    }

    public void save(String trackName) throws TrackNotFoundException {
        Track track = trackRepository.findByName(trackName);
        if (track == null)
            throw new TrackNotFoundException();

        playListRepository.add(track);
    }

    public TrackList search(String keyword) {
        return  playListRepository.search(keyword);
    }
}
