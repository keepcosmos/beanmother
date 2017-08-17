package io.jmother.core.fixture.parser;

import io.jmother.core.fixture.FixtureMap;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.Map;

/**
 * Yaml {@link FixtureParser} implementation which allows
 * to parse YAML format string.
 *
 * {@link Yaml} is used for this implementation.
 */
public class YamlFixtureParser implements FixtureParser {

    @Override
    public Map<String, FixtureMap> parse(String fixture) {
        Map<String, Object> fixtures = buildYaml().loadAs(fixture, Map.class);
        Map<String, FixtureMap> fixtureMaps = new HashMap<>();

        for (String key : fixtures.keySet()) {
            if (!(fixtures.get(key).getClass().isInstance(Map.class))) {
                fixtureMaps.put(key, new FixtureMap(key, (Map) fixtures.get(key)));
            } else {
                FixtureFormatException e = new FixtureFormatException(key, " the root of fixture data should be key - value");
                fixtureMaps.put(key, new FixtureMap(key, e));
            }
        }

        return fixtureMaps;
    }

    /**
     * Yaml is not thread-safe, initialize each parsing time.
     * @return fresh Yaml instance
     */
    private Yaml buildYaml() {
        return new Yaml();
    }
}
