package io.beanmother.core.mapper;

import io.beanmother.core.common.FixtureMap;

/**
 * The root interface for mapping {@link FixtureMap} to target instance.
 */
public interface FixtureMapper {

    /**
     * map data by the given type.
     * @return instance of T
     */
    <T> T map(FixtureMap fixtureMap, Class<T> targetType);

    /**
     * map data to target object.
     * @param fixtureMap
     * @param target
     */
    void map(FixtureMap fixtureMap, Object target);
}
