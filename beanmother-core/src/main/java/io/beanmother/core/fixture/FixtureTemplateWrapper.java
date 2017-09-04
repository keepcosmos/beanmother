package io.beanmother.core.fixture;

import java.util.List;
import java.util.Map;

/**
 *
 * Wrapper to wrap {@link List}, {@link Map} and Object link String, Number, Date, Boolean, etc in {@link FixtureTemplate}
 * A source type is, generally the return type that is parsed by {@link io.beanmother.core.fixture.parser.YamlFixtureParser}.
 */
public class FixtureTemplateWrapper {

    /**
     * Wrap Map in {@link FixtureMap}
     * @param source source map
     * @param fixtureName The key name of parent who hold the source.
     * @param parent The parent FixtureTemplate who hold the source.
     * @return fixture map
     */
    public static FixtureMap wrap(Map<String, ? extends Object> source, String fixtureName, FixtureTemplate parent) {
        FixtureMap fixtureMap = new FixtureMap();
        fixtureMap.setFixtureName(fixtureName);
        fixtureMap.setParent(parent);

        for (Map.Entry<String, ? extends Object> entry : source.entrySet()) {
            String key = entry.getKey();
            if (entry.getValue() instanceof Map) {
                fixtureMap.put(entry.getKey(), wrap((Map) entry.getValue(), key, fixtureMap));
            } else if (entry.getValue() instanceof List) {
                fixtureMap.put(entry.getKey(), wrap((List) entry.getValue(), key, fixtureMap));
            } else {
                FixtureValue wrapped = wrap(entry.getValue(), entry.getKey(), fixtureMap);
                fixtureMap.put(entry.getKey(), wrapped);
            }
        }

        return fixtureMap;
    }

    /**
     * Wrap Map in {@link FixtureList}
     * @param source source list
     * @param fixtureName The key name of parent who hold the source.
     * @param parent The parent FixtureTemplate who hold the source.
     * @return fixture list
     */
    public static FixtureList wrap(List<? extends Object> source, String fixtureName, FixtureTemplate parent) {
        FixtureList fixtureList = new FixtureList();
        fixtureList.setFixtureName(fixtureName);
        fixtureList.setParent(parent);

        for (Object object : source) {
            if (object instanceof Map) {
               fixtureList.add(wrap((Map) object, fixtureName, parent));
            } else if (object instanceof List) {
                fixtureList.add(wrap((List) object, fixtureName, parent));
            } else {
                fixtureList.add(wrap(object, fixtureName, parent));
            }
        }

        return fixtureList;
    }

    /**
     * Wrap Map in FixtureValue
     * @param source source object that must not be List or Map.
     * @param fixtureName The key name of parent who hold the source.
     * @param parent The parent FixtureTemplate who hold the source.
     * @return fixture value
     */
    public static FixtureValue wrap(Object source, String fixtureName, FixtureTemplate parent) {
        if (source instanceof Map || source instanceof List) {
            throw new IllegalArgumentException("can not wrap Map or List type of value, use #wrap after casting");
        }

        FixtureValue fixtureValue = new FixtureValue(source);
        fixtureValue.setFixtureName(fixtureName);
        fixtureValue.setParent(parent);
        return fixtureValue;
    }
}
