package io.beanmother.core.fixture;

import java.util.List;
import java.util.Map;

/**
 *
 * Converter to converte {@link List}, {@link Map} and Object link String, Number, Date, Boolean, etc to Fixture template
 * A source type is, generally the return type that is parsed by {@link io.beanmother.core.fixture.parser.YamlFixtureParser}.
 */
public class FixtureTemplateConverter {

    /**
     * Convert Map to FixtureMap
     * @param source source map
     * @param fixtureName The key name of parent who hold the source.
     * @param parent The parent FixtureTemplate who hold the source.
     * @return fixture map
     */
    public static FixtureMap convert(Map<String, ? extends Object> source, String fixtureName, FixtureTemplate parent) {
        FixtureMap fixtureMap = new FixtureMap();
        fixtureMap.setFixtureName(fixtureName);
        fixtureMap.setParent(parent);

        for (Map.Entry<String, ? extends Object> entry : source.entrySet()) {
            String key = entry.getKey();
            if (entry.getValue() instanceof Map) {
                fixtureMap.put(entry.getKey(), convert((Map) entry.getValue(), key, fixtureMap));
            } else if (entry.getValue() instanceof List) {
                fixtureMap.put(entry.getKey(), convert((List) entry.getValue(), key, fixtureMap));
            } else {
                FixtureValue converted = convert(entry.getValue(), entry.getKey(), fixtureMap);
                fixtureMap.put(entry.getKey(), converted);
            }
        }

        return fixtureMap;
    }

    /**
     * Convert Map to FixtureList
     * @param source source list
     * @param fixtureName The key name of parent who hold the source.
     * @param parent The parent FixtureTemplate who hold the source.
     * @return fixture list
     */
    public static FixtureList convert(List<? extends Object> source, String fixtureName, FixtureTemplate parent) {
        FixtureList fixtureList = new FixtureList();
        fixtureList.setFixtureName(fixtureName);
        fixtureList.setParent(parent);

        for (Object object : source) {
            if (object instanceof Map) {
               fixtureList.add(convert((Map) object, fixtureName, parent));
            } else if (object instanceof List) {
                fixtureList.add(convert((List) object, fixtureName, parent));
            } else {
                fixtureList.add(convert(object, fixtureName, parent));
            }
        }

        return fixtureList;
    }

    /**
     * Convert Map to FixtureValue
     * @param source source object that must not be List or Map.
     * @param fixtureName The key name of parent who hold the source.
     * @param parent The parent FixtureTemplate who hold the source.
     * @return fixture value
     */
    public static FixtureValue convert(Object source, String fixtureName, FixtureTemplate parent) {
        if (source instanceof Map || source instanceof List) {
            throw new IllegalArgumentException("can not convert Map or List type of value, use #convert after casting");
        }

        FixtureValue fixtureValue = new FixtureValue(source);
        fixtureValue.setFixtureName(fixtureName);
        fixtureValue.setParent(parent);
        return fixtureValue;
    }
}
