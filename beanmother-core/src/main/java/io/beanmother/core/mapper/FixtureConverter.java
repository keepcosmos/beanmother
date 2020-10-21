package io.beanmother.core.mapper;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.common.FixtureTemplate;

/**
 * The root interface for converting {@link FixtureTemplate} to the given type.
 */
public interface FixtureConverter {

    /**
     * Convert the fixtureTemplate to the give type.
     * @param fixtureTemplate fixture template
     * @param typeToken type token
     * @return converted object
     */
    Object convert(FixtureTemplate fixtureTemplate, TypeToken<?> typeToken);
}