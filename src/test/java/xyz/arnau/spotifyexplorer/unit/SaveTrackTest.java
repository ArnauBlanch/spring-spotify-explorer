package xyz.arnau.spotifyexplorer.unit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import xyz.arnau.spotifyexplorer.application.SaveTrack;
import xyz.arnau.spotifyexplorer.domain.PlayListRepository;
import xyz.arnau.spotifyexplorer.domain.Track;
import xyz.arnau.spotifyexplorer.domain.TrackNotFoundException;
import xyz.arnau.spotifyexplorer.domain.TrackRepository;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SaveTrackTest {
    private SaveTrack saveTrack;
    @Mock
    private PlayListRepository playlistRepository;
    @Mock
    private TrackRepository trackRepository;

    @Before
    public void setUp() {
        saveTrack = new SaveTrack(trackRepository, playlistRepository);
    }

    @Test
    public void addTheTrackToThePlaylist() throws TrackNotFoundException {

        Track track = new Track("an-id", "some-name", "some-singer");
        when(trackRepository.findByName("some-name")).thenReturn(track);

        saveTrack.execute("some-name");

        verify(playlistRepository).add(track);
    }

    @Test
    public void notAddTheTrackToThePlaylistWhenTrackIsNotFound() {
        when(trackRepository.findByName("unexisting-name")).thenReturn(null);

        try {
            saveTrack.execute("unexisting-name");
            fail();
        } catch (TrackNotFoundException ex) {
            verify(playlistRepository, never()).add(any());
        }
    }
}
