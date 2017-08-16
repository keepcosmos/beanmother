package io.jmother.core.fixture.parser;

import org.yaml.snakeyaml.Yaml;

import java.util.Map;

/**
 * Yaml {@link FixtureParser} implementation which allows
 * to parse YAML format string.
 *
 * {@link Yaml} is used for this implementation.
 */
public class YamlFixtureParser implements FixtureParser {

    @Override
    public Map<String, Object> parse(String fixture) {
        return buildYaml().loadAs(fixture, Map.class);
    }

    /**
     * Yaml is not thread-safe, initialize each parsing time.
     * @return fresh Yaml instance
     */
    private Yaml buildYaml() {
        return new Yaml();
    }
}
