import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Map.Entry.comparingByValue;

public class SongCacheImpl implements SongCache{
    private final Map<String, Integer> songCache;

    public SongCacheImpl() {
        // multi-threading safe
        this.songCache = new ConcurrentHashMap<>();
    }

    @Override
    public void recordSongPlays(String songId, int numPlays) {
        if (numPlays<0) throw new IllegalArgumentException("play number can't not be negative");
        if (songCache.containsKey(songId)){
            int numPlayed = songCache.get(songId);
            int num = numPlays + numPlayed;
            songCache.put(songId, num);
        } else {
            songCache.put(songId, numPlays);
        }

    }

    @Override
    public int getPlaysForSong(String songId) {
        return songCache.getOrDefault(songId,-1);
    }

    @Override
    public List<String> getTopNSongsPlayed(int n) {
        if (n<0) throw new IllegalArgumentException("n can't not be negative");
        if (n == 0) return null;
        // topNSongs needs to be a minHeap
        PriorityQueue<String> topNSongs = new PriorityQueue<>(n,
                (a, b) -> songCache.get(a) - songCache.get(b));
        for (String s : songCache.keySet()){
            if (topNSongs.size()<n){
                topNSongs.add(s);
            }else{
                String key = topNSongs.peek();
                if (songCache.get(key) < songCache.get(s)){
                    topNSongs.poll();
                    topNSongs.add(s);
                }
            }
        }
        return topNSongs.stream().toList();
//        List<String> topNSongs = new ArrayList<>();
//        songCache.entrySet()
//                .stream()
//                .sorted(comparingByValue(Comparator.reverseOrder()))
//                .forEachOrdered(x -> topNSongs.add(x.getKey()));
//        return topNSongs;
    }
}
