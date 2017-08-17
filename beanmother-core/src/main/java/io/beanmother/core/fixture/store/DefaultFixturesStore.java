package io.beanmother.core.fixture.store;

import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.fixture.parser.FixtureParser;
import io.beanmother.core.fixture.parser.YamlFixtureParser;
import io.beanmother.core.fixture.scanner.FixtureScanner;
import io.beanmother.core.fixture.scanner.YamlFixtureScanner;
import io.beanmother.core.util.ClassUtils;
import io.beanmother.core.util.Location;

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
public class DefaultFixturesStore implements FixturesStore {

    private static Logger logger = Logger.getLogger(DefaultFixturesStore.class.getName());

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
    private Map<String, FixtureMap> fixtureMaps;

    /**
     * Create a default fixture store.
     */
    public DefaultFixturesStore() {
        this(ClassUtils.getDefaultClassLoader());
    }

    /**
     * Create a default fixture store.
     */
    public DefaultFixturesStore(ClassLoader classLoader) {
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
    public void addLocation(Location location) throws IOException {
        if (fixtureLocations.contains(location)) {
            logger.warning(location.getDescriptor() + " is already added.");
            return;
        }

        List<File> files = fixtureScanner.scan(location);

        if (files.size() == 0) {
            logger.warning("can not find any fixture file in " + location.getDescriptor());
            return;
        }

        Map<String, FixtureMap> parsed = new HashMap<>();
        for (File file : files) {
            if (fixtureFiles.contains(file)) continue;
            String fixtureStr = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            parsed.putAll(fixtureParser.parse(fixtureStr));
        }

        fixtureFiles.addAll(files);
        fixtureLocations.add(location);
        fixtureMaps.putAll(parsed);
    }

    @Override
    public void reset() {
        fixtureLocations = new HashSet<>();
        fixtureFiles = new HashSet<>();
        fixtureMaps = new HashMap<>();
    }

    public Set<Location> getFixtureLocations() {
        return fixtureLocations;
    }

    public Set<File> getFixtureFiles() {
        return fixtureFiles;
    }

    public Map<String, FixtureMap> getFixtureMap() {
        return fixtureMaps;
    }
}
