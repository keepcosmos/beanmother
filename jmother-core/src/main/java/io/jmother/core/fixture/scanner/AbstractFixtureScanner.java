package io.jmother.core.fixture.scanner;

import io.jmother.core.fixture.Location;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Abstract Scanner to find fixture files.
 */
public abstract class AbstractFixtureScanner implements FixtureScanner {

    private static Logger logger = Logger.getLogger(AbstractFixtureScanner.class.getName());

    /**
     * The Classloader to use.
     */
    private ClassLoader classLoader;

    public AbstractFixtureScanner(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public List<File> scan(Location location) {
        String path = getAbsolutePath(location);
        if (path == null) {
            logger.warning(location.getDescriptor() + " is ignored, because it does not exist.");
            return new ArrayList<>();
        }

        Set<File> files = listOfAllFixtureFiles(path);
        return new ArrayList<>(files);
    }

    /**
     * Check the file is a fixture file or not.
     * Subclass can override this method to determine which file is a fixture file.
     * @param file
     * @return {@code true} if the file is a fixture file.
     */
    protected boolean isFixtureFile(File file) {
        return true;
    }

    /**
     * find absolute paths from location
     * @return absolute path as a string.
     */
    private String getAbsolutePath(Location location) {
        if (location.isClasspath()) {
            URL url = this.classLoader.getResource(location.getPath());
            if (url == null) return null;
            return url.getFile();
        } else  if (location.isFilesystemPath()) {
            File file = new File(location.getPath());
            if (!file.exists()) return null;
            return file.getAbsolutePath();
        } else {
            return null;
        }
    }

    /**
     * Find all files recursively under directories, except {@link #isFixtureFile(File)}
     * @param file fixture file or directory
     * @return files
     */
    private Set<File> listOfAllFixtureFiles(File file) {
        Set<File> files = new HashSet<>();

        if (file.isDirectory()) {
            for (File subfile : file.listFiles()) {
                if(subfile.isDirectory()) {
                    files.addAll(listOfAllFixtureFiles(subfile));
                } else {
                    if (isFixtureFile(subfile)) files.add(subfile);
                }
            }
        } else {
            if (isFixtureFile(file)) files.add(file);
        }
        return files;
    }

    private Set<File> listOfAllFixtureFiles(String path) {
        return listOfAllFixtureFiles(new File(path));
    }
}
