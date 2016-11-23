package hdgh0g.albumfinder;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class TestUtils {

    private final static String TEST_FOLDER_NAME = "albumfinder_tests";
    private final static String TEST_SUBFOLDER_NAME = "subfolder";

    public static File testFolder() {
        return new File(FileUtils.getTempDirectory(), TEST_FOLDER_NAME);
    }

    public static File testSubfolder() {
        return new File(testFolder(), TEST_SUBFOLDER_NAME);
    }
}
