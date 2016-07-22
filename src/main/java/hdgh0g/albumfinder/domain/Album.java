package hdgh0g.albumfinder.domain;

public class Album {

    private Artist albumArtist;

    private String name;

    private Integer year;

    public Album(Artist albumArtist, String name, Integer year) {
        this.albumArtist = albumArtist;
        this.name = name;
        this.year = year;
    }

    public Album(Artist albumArtist, String name) {
        this.albumArtist = albumArtist;
        this.name = name;
    }

    public Artist getAlbumArtist() {
        return albumArtist;
    }

    public String getName() {
        return name;
    }

    public Integer getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Album album = (Album) o;
        return album.name.equals(name) && album.albumArtist.equals(albumArtist);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return albumArtist + " " + name;
    }
}
