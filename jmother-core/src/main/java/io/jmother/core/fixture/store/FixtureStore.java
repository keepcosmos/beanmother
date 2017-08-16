package io.jmother.core.fixture.store;

import io.jmother.core.util.Location;

import java.util.Map;

/**
 * The root interface for storing raw fixture data.
 *
 * It can add fixture file locations and {@code #refresh} for loading and parsing fixture file.
 */
public interface FixtureStore {

    /**
     *
     * @param fixtureKey root key of a fixture
     * @return
     */
    Map<String, Object> get(String fixtureKey);
    void addLocation(Location location);
    void reset();
    void refresh();
}
