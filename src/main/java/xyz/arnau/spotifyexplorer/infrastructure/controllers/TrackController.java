package xyz.arnau.spotifyexplorer.infrastructure.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.arnau.spotifyexplorer.infrastructure.controllers.model.SaveTrackRequest;
import xyz.arnau.spotifyexplorer.application.SaveTrack;
import xyz.arnau.spotifyexplorer.domain.TrackNotFoundException;
import xyz.arnau.spotifyexplorer.infrastructure.controllers.model.SearchTrackRequest;

@RestController
@RequestMapping("/tracks")
public class TrackController {

    private SaveTrack saveTrack;

    public TrackController(SaveTrack saveTrack) {
        this.saveTrack = saveTrack;
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveTrack(@RequestBody SaveTrackRequest request) {
        try {
            saveTrack.execute(request.getName());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (TrackNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchTrack(@RequestParam String keyword) {
        try {
            //saveTrack.execute(request.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
