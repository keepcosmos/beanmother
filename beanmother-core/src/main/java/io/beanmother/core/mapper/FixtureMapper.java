package io.beanmother.core.mapper;

import io.beanmother.core.fixture.FixtureMap;

import java.util.Map;

/**
 * The interface for mapping {@link Map<String, Object>}(from fixture file) to target instance.
 */
public interface FixtureMapper {
    /**
     * map data to instance of T.
     * @return instance of T
     */
    <T> T map(FixtureMap fixtureMap, Class<T> targetType);

    /**
     * map data to obj.
     * @return
     */
    <T> void map(FixtureMap fixtureMap, T targetObject);
}
