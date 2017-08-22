package io.beanmother.core.fixture.store;

import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.util.Location;

import java.io.IOException;

/**
 * The root interface for storing fixtureMap data.
 *
 * It can add fixture file locations and {@code #refresh} for loading and parsing fixture file.
 */
public interface FixturesStore {
    FixtureMap get(String fixtureKey);
    void addLocation(Location location) throws IOException;
    void reset();
}
