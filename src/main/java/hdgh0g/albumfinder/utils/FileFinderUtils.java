package hdgh0g.albumfinder.utils;

import org.jaudiotagger.audio.AudioFileFilter;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FileFinderUtils {

    private static AudioFileFilter audioFileFilter = new AudioFileFilter(false);

    public static Set<File> findMusicFilesInFolders(Boolean recursively, File ... folders) throws IOException {
        List<String> args = new ArrayList<>();
        for (File folder : folders) {
            args.add(folder.getCanonicalPath());
        }
        return findMusicFilesInFolders(recursively, args.toArray(new String[args.size()]));
    }

    public static Set<File> findMusicFilesInFolders(Boolean recursively, String ... folders) throws IOException {
        Set<File> foundFiles = new HashSet<>();
        for (String folder : folders) {
            foundFiles.addAll(findMusicFilesInFolder(recursively, folder));
        }
        return foundFiles;
    }

    private static Collection<File> findMusicFilesInFolder(Boolean recursively, String folder) throws IOException {
        return findMusicFilesInFolder(recursively,new File(folder));
    }

    private static Collection<File> findMusicFilesInFolder(Boolean recursively, File directory) throws IOException {
        if (!directory.exists()) {
            throw new IllegalArgumentException("folder argument should represent existing folder");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("folder argument should be a folder");
        }
        File[] directoryFiles = directory.listFiles();
        if (directoryFiles == null) {
            throw new IOException("cant read from folder");
        }

        if (!recursively) {
            return Arrays.stream(directoryFiles).filter(file -> !file.isDirectory()).filter(FileFinderUtils::isMusicFile).collect(Collectors.toList());
        } else {
            Set<File> foundFiles = new HashSet<>();
            for (File file : directoryFiles) {
                if (file.isDirectory()) {
                    foundFiles.addAll(findMusicFilesInFolder(true, file));
                }
                else {
                    if (isMusicFile(file)) {
                        foundFiles.add(file);
                    }
                }
            }
            return foundFiles;
        }
    }

    private static boolean isMusicFile(File file) {
         return audioFileFilter.accept(file);
    }
}
