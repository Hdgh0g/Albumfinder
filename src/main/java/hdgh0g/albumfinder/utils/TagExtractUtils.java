package hdgh0g.albumfinder.utils;

import hdgh0g.albumfinder.domain.Album;
import hdgh0g.albumfinder.domain.Artist;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TagExtractUtils {

    public static Set<Album> getAllAlbumFromFiles(File ... files) {
        if (files == null) {
            return new HashSet<>();
        }
        return Arrays.stream(files).map(TagExtractUtils::getAlbumFromFile).filter(album -> album != null).collect(Collectors.toSet());
    }

    private static Album getAlbumFromFile(File file) {
        AudioFile audioFile;
        try {
            audioFile = AudioFileIO.read(file);
        } catch (Exception e) {
            return null;
        }
        Tag tag = audioFile.getTag();
        String artist = tag.getFirst(FieldKey.ARTIST);
        String album = tag.getFirst(FieldKey.ALBUM);
        Integer year = Integer.valueOf(tag.getFirst(FieldKey.YEAR));
        if (artist != null && album != null) {
            if (year != null) {
                return new Album(new Artist(artist), album, year);
            }
            return new Album(new Artist(artist), album);
        }
        return null;
    }
}
