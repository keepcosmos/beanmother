package io.beanmother.core.loader.store;

import io.beanmother.core.common.FixtureMap;
import io.beanmother.core.loader.Location;

/**
 * The root interface for storing fixtureMap data.
 *
 * It can add fixture file locations and {@code #refresh} for loading and parsing fixture file.
 */
public interface FixturesStore {

    FixtureMap get(String fixtureKey);

    FixtureMap reproduce(String fixtureKey);

    boolean exists(String fixtureKey);

    void addLocation(Location location);

    void reset();
}