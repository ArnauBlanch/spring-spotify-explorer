package xyz.arnau.spotifyexplorer.infrastructure.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.arnau.spotifyexplorer.domain.TrackList;
import xyz.arnau.spotifyexplorer.infrastructure.controllers.model.SaveTrackRequest;
import xyz.arnau.spotifyexplorer.application.TrackService;
import xyz.arnau.spotifyexplorer.domain.TrackNotFoundException;

@RestController
@RequestMapping("/tracks")
public class TrackController {

    private TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveTrack(@RequestBody SaveTrackRequest request) {
        try {
            trackService.save(request.getName());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (TrackNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchTrack(@RequestParam String keyword) {
        try {
            TrackList trackList = trackService.search(keyword);
            return ResponseEntity.ok(trackList);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
