import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SongCacheImplTest {

    private SongCache cache;

    @BeforeEach
    public void createCache(){
        cache = new SongCacheImpl();
        cache.recordSongPlays("ID-1", 3);
        cache.recordSongPlays("ID-1", 1);
        cache.recordSongPlays("ID-2", 2);
        cache.recordSongPlays("ID-3", 5);
    }

    @Test
    void recordSongPlays() {
        assertThrows(IllegalArgumentException.class, () -> cache.recordSongPlays("ID-2", -1));
        cache.recordSongPlays("ID-1", 0);

    }

    @Test
    void getPlaysForSong() {
        assertEquals(cache.getPlaysForSong("ID-1"), 4);
        assertEquals(cache.getPlaysForSong("ID-9"), -1);
    }

    @Test
    void getTopNSongsPlayed() {
        assertThrows(IllegalArgumentException.class, () -> cache.getTopNSongsPlayed(-1));
        assertTrue(cache.getTopNSongsPlayed(2).contains("ID-3"));
        assertTrue(cache.getTopNSongsPlayed(2).contains("ID-1"));
        assertNull(cache.getTopNSongsPlayed(0));
    }

    public void recoverCache(){
        cache = null;
    }
}