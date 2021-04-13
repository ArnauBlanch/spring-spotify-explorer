package xyz.arnau.spotifyexplorer.infrastructure.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.arnau.spotifyexplorer.infrastructure.controllers.model.SaveTrackRequest;
import xyz.arnau.spotifyexplorer.application.SaveTrack;
import xyz.arnau.spotifyexplorer.domain.TrackNotFoundException;

@RestController
public class TrackController {

    private SaveTrack saveTrack;

    public TrackController(SaveTrack saveTrack) {
        this.saveTrack = saveTrack;
    }

    @PostMapping("/track")
    public ResponseEntity<?> saveTrack(@RequestBody SaveTrackRequest request) {
        try {
            saveTrack.execute(request.getName());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (TrackNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}