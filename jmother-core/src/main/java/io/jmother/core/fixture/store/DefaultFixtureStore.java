package io.jmother.core.fixture.store;

import io.jmother.core.fixture.parser.FixtureParser;
import io.jmother.core.fixture.parser.YamlFixtureParser;
import io.jmother.core.fixture.scanner.FixtureScanner;
import io.jmother.core.fixture.scanner.YamlFixtureScanner;
import io.jmother.core.util.ClassUtils;
import io.jmother.core.util.Location;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

/**
 * Default fixture store.
 * It uses {@link YamlFixtureScanner} and {@link YamlFixtureParser} for loading and parsing fixture files.
 */
public class DefaultFixtureStore implements FixtureStore {

    private static Logger logger = Logger.getLogger(DefaultFixtureStore.class.getName());

    private ClassLoader classLoader;

    /**
     * Scanner to load fixture files.
     */
    private FixtureScanner fixtureScanner;

    /**
     * Parser to map fixture string to Map
     */
    private FixtureParser fixtureParser;

    /**
     * Locations to load fixture files.
     */
    private Set<Location> fixtureLocations;

    /**
     * Fixture files
     */
    private Set<File> fixtureFiles;

    /**
     * Fixtures that are parsed.
     */
    private Map<String, Object> fixtureMap;

    /**
     * Create a default fixture store.
     */
    public DefaultFixtureStore() {
        this(ClassUtils.getDefaultClassLoader());
    }

    /**
     * Create a default fixture store.
     */
    public DefaultFixtureStore(ClassLoader classLoader) {
        this.classLoader = classLoader;
        this.fixtureScanner = new YamlFixtureScanner(classLoader);
        this.fixtureParser = new YamlFixtureParser();

        reset();
    }

    @Override
    public Map<String, Object> get(String fixtureKey) {
        return null;
    }

    @Override
    public void addLocation(Location location) {
        if (fixtureLocations.contains(location)) {
            logger.warning(location.getDescriptor() + " is already added.");
            return;
        }

        fixtureLocations.add(location);
        List<File> files = fixtureScanner.scan(location);

        if (files.size() == 0) {
            logger.warning("can not find any fixture file in " + location.getDescriptor());
            return;
        }

        fixtureFiles.addAll(files);
    }

    @Override
    public void reset() {
        fixtureLocations = new HashSet<>();
        fixtureFiles = new HashSet<>();
        fixtureMap = new HashMap<>();
    }

    @Override
    public void refresh() {
        fixtureMap = new HashMap<>();
        for(File file : fixtureFiles) {
            String fixtureStr;
            try {
                fixtureStr = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            Map<String, Object> fixture = fixtureParser.parse(fixtureStr);

            fixtureMap.putAll(fixture);
        }
    }

    public Set<Location> getFixtureLocations() {
        return fixtureLocations;
    }

    public Set<File> getFixtureFiles() {
        return fixtureFiles;
    }

    public Map<String, Object> getFixtureMap() {
        return fixtureMap;
    }
}
