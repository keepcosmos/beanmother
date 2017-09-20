package io.beanmother.core.loader.store;

import io.beanmother.core.common.FixtureMap;
import io.beanmother.core.loader.Location;

/**
 * The root interface for storing fixtureMap data.
 *
 * It can add fixture yml file locations and find a FixtureMap by key.
 */
public interface FixturesStore {

    /**
     * Get a FixtureMap.
     * @param fixtureKey the fixtureKey
     * @return the FixtureMap
     */
    FixtureMap get(String fixtureKey);

    /**
     * Reproduce a FixtureMap.
     * @param fixtureKey the fixtureKey
     * @return the reproduced FixtureMap
     */
    FixtureMap reproduce(String fixtureKey);

    /**
     * Check if a FixtureMap exists.
     * @param fixtureKey the fixtureKey
     */
    boolean exists(String fixtureKey);

    /**
     * Add fixture yml files location.
     * @param location the location
     */
    void addLocation(Location location);

    /**
     * Reset a FixtureStore.
     */
    void reset();
}