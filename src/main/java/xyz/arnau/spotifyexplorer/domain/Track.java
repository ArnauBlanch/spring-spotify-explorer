package xyz.arnau.spotifyexplorer.domain;

import java.util.Objects;

public class Track {
    private final String id;
    private final String name;
    private final String singer;

    public Track(String id, String name, String singer) {
        this.id = id;
        this.name = name;
        this.singer = singer;
    }

//    @Override
//    public String toString() {
//        return "{" +
//                "id=" + id +
//                ", name=" + name +
//                ", singer=" + singer +
//                '}';
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return Objects.equals(id, track.id) && Objects.equals(name, track.name) && Objects.equals(singer, track.singer);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSinger() {
        return singer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, singer);
    }
}
