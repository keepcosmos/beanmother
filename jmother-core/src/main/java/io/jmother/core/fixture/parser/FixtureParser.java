package io.jmother.core.fixture.parser;

import java.util.Map;

/**
 * The root interface for parsing fixture string to {@link java.util.Map<String, Object>}
 */
public interface FixtureParser {
    /**
     * Parse fixture string to Map
     * @return fixture map
     */
    Map<String, Object> parse(String fixture);
}
