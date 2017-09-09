package io.beanmother.core.fixture.store;

import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.fixture.parser.FixtureParser;
import io.beanmother.core.fixture.parser.YamlFixtureParser;
import io.beanmother.core.fixture.scanner.FixtureScanner;
import io.beanmother.core.fixture.scanner.YamlFixtureScanner;
import io.beanmother.core.mapper.DefaultFixtureMapper;
import io.beanmother.core.util.ClassUtils;
import io.beanmother.core.util.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Default fixture store.
 * It uses {@link YamlFixtureScanner} and {@link YamlFixtureParser} for loading and parsing fixture files.
 */
public class DefaultFixturesStore implements FixturesStore {

    private final static Logger logger = LoggerFactory.getLogger(DefaultFixtureMapper.class);

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
     * Fixtures
     */
    private Map<String, FixtureMap> fixtureMaps;

    /**
     * Create a default fixture store.
     */
    public DefaultFixturesStore() {
        this(new YamlFixtureScanner(ClassUtils.getDefaultClassLoader()), new YamlFixtureParser());
    }

    /**
     * Create a default fixture store.
     */
    public DefaultFixturesStore(FixtureScanner fixtureScanner, FixtureParser fixtureParser) {
        this.fixtureScanner = fixtureScanner;
        this.fixtureParser = fixtureParser;
        reset();
    }

    @Override
    public FixtureMap get(String fixtureKey) {
        return this.fixtureMaps.get(fixtureKey);
    }

    @Override
    public FixtureMap reproduce(String fixtureKey) {
        FixtureMap fixtureMap = get(fixtureKey);
        return fixtureMap == null ? null : fixtureMap.reproduce();
    }

    @Override
    public boolean exists(String fixtureKey) {
        return this.fixtureMaps.containsKey(fixtureKey);
    }

    @Override
    public void addLocation(Location location) {
        if (fixtureLocations.contains(location)) {
            logger.debug(location.getDescriptor() + " is already added.");
            return;
        }

        List<File> files = fixtureScanner.scan(location);

        if (files.size() == 0) {
            logger.warn("can not find any fixture file in " + location.getDescriptor());
            return;
        }

        Map<String, FixtureMap> parsed = new HashMap<>();
        for (File file : files) {
            if (fixtureFiles.contains(file)) continue;
            String fixtureStr = null;
            try {
                fixtureStr = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            } catch (IOException e) {
                throw new RuntimeException("can not read " + file.getAbsolutePath(), e);
            }
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

    /**
     * Get registered fixture locations
     * @return
     */
    public Set<Location> getFixtureLocations() {
        return fixtureLocations;
    }

    /**
     * Get registered fixture files
     * @return
     */
    public Set<File> getFixtureFiles() {
        return fixtureFiles;
    }

    /**
     * Get fixtureMap
     * @return
     */
    public Map<String, FixtureMap> getFixtureMaps() {
        return fixtureMaps;
    }
}
