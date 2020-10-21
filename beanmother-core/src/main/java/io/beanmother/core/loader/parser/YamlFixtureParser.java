package io.beanmother.core.loader.parser;

import io.beanmother.core.common.FixtureMap;
import io.beanmother.core.loader.FixtureTemplateWrapper;
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


    @SuppressWarnings("unchecked")
    @Override
    public Map<String, FixtureMap> parse(String fixture) {
        Map<String, ?> fixtures = buildYaml().loadAs(fixture, Map.class);
        Map<String, FixtureMap> fixtureMaps = new HashMap<>();

        for (Map.Entry<String, ?> entry : fixtures.entrySet()) {
            if (entry.getValue() instanceof Map) {
                FixtureMap fixtureMap = FixtureTemplateWrapper.wrap((Map<String, ?>) entry.getValue(), entry.getKey(), null);
                fixtureMap.setRoot(true);
                fixtureMaps.put(entry.getKey(), fixtureMap);
            } else {
                throw new FixtureFormatException(entry.getKey(), " the root of fixture data should be key - value");
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
