package io.beanmother.core.loader.scanner;

import io.beanmother.core.loader.Location;

import java.io.File;
import java.util.List;

/**
 * The root interface for scanning fixture files
 */
public interface FixtureScanner {

    /**
     * Find all fixture files in the location.
     * @param location location
     * @return All fixture files.
     */
    List<File> scan(Location location);
}
