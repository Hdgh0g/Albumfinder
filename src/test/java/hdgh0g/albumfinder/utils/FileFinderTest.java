package hdgh0g.albumfinder.utils;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class FileFinderTest {

    @Test
    public void testRecursivelyFileSearch() throws IOException {
        Set<File> files = FileFinderUtils.findMusicFilesInFolders(true,"/media/hdgh0g/Case/Share/Music/FLAC", "/media/hdgh0g/Case/Share/Music/MP3");
        files.toString();
    }

}