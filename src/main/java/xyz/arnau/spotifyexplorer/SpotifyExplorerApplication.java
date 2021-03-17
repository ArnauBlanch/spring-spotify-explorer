package xyz.arnau.spotifyexplorer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SpotifyExplorerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotifyExplorerApplication.class, args);
	}
}
