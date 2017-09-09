package io.beanmother.core.mapper.preprocessor;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.fixture.FixtureMap;

/**
 * PreProcessor that runs before mapping.
 */
public interface FixtureMappingPreProcessor {
    void process(FixtureMap fixtureMap, TypeToken targetType);
}