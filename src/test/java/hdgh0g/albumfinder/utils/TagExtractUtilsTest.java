package hdgh0g.albumfinder.utils;

import hdgh0g.albumfinder.domain.Album;
import hdgh0g.albumfinder.domain.Artist;
import org.apache.commons.io.FileUtils;
import org.hamcrest.MatcherAssert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import static hdgh0g.albumfinder.TestUtils.testFolder;
import static hdgh0g.albumfinder.utils.TagExtractUtils.getAllAlbumFromFiles;
import static org.hamcrest.Matchers.*;

public class TagExtractUtilsTest {

    @AfterClass
    public static void cleanUp() throws IOException {
        FileUtils.deleteDirectory(testFolder());
    }

    @BeforeClass
    public static void createFileStructure() throws IOException {
        File testFolder = testFolder();

        InputStream resourceInputStream = FileFinderTest.class.getClassLoader().getResourceAsStream("testfiles/test_flac.flac");
        FileUtils.copyInputStreamToFile(resourceInputStream, new File(testFolder,"test_flac.flac"));

        resourceInputStream = FileFinderTest.class.getClassLoader().getResourceAsStream("testfiles/test_mp3.mp3");
        FileUtils.copyInputStreamToFile(resourceInputStream, new File(testFolder, "test_mp3.mp3"));
    }

    @Test
    public void testGetAlbumFromEmptyFile() {
        Set<Album> albums = getAllAlbumFromFiles(null, null);
        MatcherAssert.assertThat("empty set", albums, empty());
    }

    @Test
    public void testGetAlbumFromFlacFile() {
        Set<Album> albums = getAllAlbumFromFiles(new File(testFolder(),"test_flac.flac"));
        Album flacAlbum = albums.iterator().next();
        MatcherAssert.assertThat("Artist", flacAlbum.getAlbumArtist(), equalTo(new Artist("Александр Пушной")));
        MatcherAssert.assertThat("Album", flacAlbum.getName(), equalTo("#недошуток"));
        MatcherAssert.assertThat("Year", flacAlbum.getYear(), is(2015));
    }

    @Test
    public void testGetAlbumFromTwoSameFiles() {
        File file = new File(testFolder(),"test_flac.flac");
        Set<Album> albums = getAllAlbumFromFiles(file, file);
        MatcherAssert.assertThat("empty set", albums, hasSize(1));
    }

    @Test
    public void testGetAlbumFromTwoFiles() {
        Set<Album> albums = getAllAlbumFromFiles(new File(testFolder(),"test_flac.flac"), new File(testFolder(),"test_mp3.mp3"));
        MatcherAssert.assertThat("empty set", albums, hasSize(2));
    }
}