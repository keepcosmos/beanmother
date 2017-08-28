package io.beanmother.core.fixture.parser;

import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.fixture.FixtureTemplateConverter;
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
        Map<String, Object> fixtures = buildYaml().loadAs(fixture, Map.class);;
        Map<String, FixtureMap> fixtureMaps = new HashMap<>();

        for (String key : fixtures.keySet()) {
            if (fixtures.get(key) instanceof Map) {
                FixtureMap fixtureMap = FixtureTemplateConverter.convert((Map) fixtures.get(key), key, null);
                fixtureMap.setRoot(true);
                fixtureMaps.put(key, fixtureMap);
            } else {
                throw new FixtureFormatException(key, " the root of fixture data should be key - value");
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
