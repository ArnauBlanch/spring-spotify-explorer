package xyz.arnau.spotifyexplorer.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrackController {

    @PostMapping("/track")
    public ResponseEntity<?> saveTrack() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
