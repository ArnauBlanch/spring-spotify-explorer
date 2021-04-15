package xyz.arnau.spotifyexplorer.unit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import xyz.arnau.spotifyexplorer.application.TrackService;
import xyz.arnau.spotifyexplorer.domain.*;

import java.util.List;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrackServiceTest {
    private TrackService trackService;
    @Mock
    private PlayListRepository playlistRepository;
    @Mock
    private TrackRepository trackRepository;

    @Before
    public void setUp() {
        trackService = new TrackService(trackRepository, playlistRepository);
    }

    @Test
    public void addTheTrackToThePlaylist() throws TrackNotFoundException {

        Track track = new Track("an-id", "some-name", "some-singer");
        when(trackRepository.findByName("some-name")).thenReturn(track);

        trackService.save("some-name");

        verify(playlistRepository).add(track);
    }

    @Test
    public void notAddTheTrackToThePlaylistWhenTrackIsNotFound() {
        when(trackRepository.findByName("unexisting-name")).thenReturn(null);

        try {
            trackService.save("unexisting-name");
            fail();
        } catch (TrackNotFoundException ex) {
            verify(playlistRepository, never()).add(any());
        }
    }

    @Test
    public void givenKeywordReturnsListOfTracks() {
        String keyword = "someKeyword";
        Track track = new Track("an-id", "some-name", "some-singer");
        TrackList expectedTracklist = new TrackList(List.of(track));

        when(playlistRepository.search(keyword)).thenReturn(expectedTracklist);
        TrackList trackList = trackService.search(keyword);

        verify(playlistRepository, times(1)).search(keyword);
        assert(trackList).equals(expectedTracklist);
    }
}
