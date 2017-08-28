package io.beanmother.core.mapper;

import io.beanmother.core.fixture.FixtureMap;

/**
 * The root interface for mapping {@link FixtureMap} to target instance.
 */
public interface FixtureMapper {

    /**
     * map data to instance of T.
     * @return instance of T
     */
    <T> T map(FixtureMap fixtureMap, Class<T> targetType);
}
