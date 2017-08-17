package io.jmother.core.fixture.store;

import io.jmother.core.util.Location;

import java.io.IOException;
import java.util.Map;

/**
 * The root interface for storing fixtureMap data.
 *
 * It can add fixture file locations and {@code #refresh} for loading and parsing fixture file.
 */
public interface FixturesStore {
    Map<String, Object> get(String fixtureKey);
    void addLocation(Location location) throws IOException;
    void reset();
}
