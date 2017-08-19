package io.beanmother.core.mapper;

import io.beanmother.core.fixture.FixtureMap;

/**
 * PreProcessor that runs before mapping.
 */
public interface FixtureMappingPreProcessor {
    void process(FixtureMap fixtureMap, Class targetType);
}