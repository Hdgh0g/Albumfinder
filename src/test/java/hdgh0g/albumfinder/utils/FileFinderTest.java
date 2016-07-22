package hdgh0g.albumfinder.utils;

import org.apache.commons.io.FileUtils;
import org.hamcrest.MatcherAssert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;

public class FileFinderTest {

    private final static String TEST_FOLDER_NAME = "albumfinder_tests";
    private final static String TEST_SUBFOLDER_NAME = "subfolder";

    @AfterClass
    public static void cleanUp() throws IOException {
        FileUtils.deleteDirectory(testFolder());
    }

    @BeforeClass
    public static void createFileStructure() throws IOException {
        File testFolder = testFolder();
        File testSubfolder = new File(testFolder, TEST_SUBFOLDER_NAME);

        InputStream resourceInputStream = FileFinderTest.class.getClassLoader().getResourceAsStream("testfiles/test_flac.flac");
        FileUtils.copyInputStreamToFile(resourceInputStream, new File(testFolder,"test_flac.flac"));

        resourceInputStream = FileFinderTest.class.getClassLoader().getResourceAsStream("testfiles/test_image.jpg");
        FileUtils.copyInputStreamToFile(resourceInputStream, new File(testFolder,"test_image.jpg"));

        resourceInputStream = FileFinderTest.class.getClassLoader().getResourceAsStream("testfiles/test_mp3.mp3");
        FileUtils.copyInputStreamToFile(resourceInputStream, new File(testSubfolder, "test_mp3.mp3"));
    }

    @Test
    public void testRecursivelyFileSearch() throws IOException {
        Set<File> files = FileFinderUtils.findMusicFilesInFolders(true, testFolder());
        MatcherAssert.assertThat("found all", files.size(), is(2));
    }

    @Test
    public void testFileSearch() throws IOException {
        Set<File> files = FileFinderUtils.findMusicFilesInFolders(false, testFolder());
        MatcherAssert.assertThat("found only in needed folder", files.size(), is(1));
    }

    @Test
    public void testFileSearchFromTwoFolders() throws IOException {
        Set<File> files = FileFinderUtils.findMusicFilesInFolders(false, testFolder(), new File(testFolder(), TEST_SUBFOLDER_NAME));
        MatcherAssert.assertThat("found from all", files.size(), is(2));
    }

    @Test
    public void testFileSearchFromSameFolders() throws IOException {
        Set<File> files = FileFinderUtils.findMusicFilesInFolders(false, testFolder(), testFolder());
        MatcherAssert.assertThat("found only once", files.size(), is(1));
    }

    private static File testFolder() {
        return new File(FileUtils.getTempDirectory(), TEST_FOLDER_NAME);
    }
}