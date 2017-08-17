package io.beanmother.core.mapper;

import java.util.Map;

/**
 * The interface for mapping {@link Map<String, Object>}(from fixture file) to target instance.
 */
public interface FixtureMapper {
    /**
     * map data to instance of T.
     * @return instance of T
     */
    <T> T map(Map<String, Object> data, Class<T> targetType);

    /**
     * map data to obj.
     * @return
     */
    <T> T map(Map<String, Object> data, T targetObject);
}
