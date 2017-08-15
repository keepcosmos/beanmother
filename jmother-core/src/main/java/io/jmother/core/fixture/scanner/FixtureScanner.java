package io.jmother.core.fixture.scanner;

import io.jmother.core.fixture.Location;

import java.io.File;
import java.util.List;

/**
 * The root interface for scanning fixture files
 */
public interface FixtureScanner {

    /**
     * Find all fixture files in the location.
     * @param location
     * @return All fixture files.
     */
    List<File> scan(Location location);
}
