package io.beanmother.core.util;

import io.beanmother.core.fixture.FixtureMap;

import java.util.List;
import java.util.Map;

/**
 * It traverse(DFS) FixtureMap's all edge elements
 *
 * FixtureMap that is parsed by {@link io.beanmother.core.fixture.parser.YamlFixtureParser} has only Map and List structure and String key.
 *
 */
public class FixtureMapUtil {

    /**
     * The interface that runs when visit edges.
     */
    interface Processor {

        /**
         * Run when visiting edge.
         * @return return value will re-assign to the key.
         */
        Object visit(String key, Object object);
    }

    /**
     * Traverse each edges of fixtureMap and run {@link Processor} when it meets edge.
     */
    public static void traverse(FixtureMap fixtureMap, Processor processor) {
        for (Map.Entry<String, Object> entry : fixtureMap.entrySet()) {
            entry.setValue(_traverse(entry.getKey(), entry.getValue(), processor));
        }
    }

    /**
     * Convert {@link Map<String, Object>} to {@link FixtureMap}.
     * @param map Map object that is parsed by {@link io.beanmother.core.fixture.parser.YamlFixtureParser} generally.
     * @return fixtureMap
     */
    public static FixtureMap convertMapToFixtureMap(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {

        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static Object _traverse(String key, Object data, Processor processor) {
        if ((data instanceof Map)) {
            for (Map.Entry<String, Object> entry : ((Map<String, Object>) data).entrySet()) {
                entry.setValue(_traverse(entry.getKey(), entry.getValue(), processor));
            }
        } else if ((data instanceof List)) {
            List<Object> dataAsList = (List) data;
            for ( int i = 0 ; i < dataAsList.size() ; i++) {
                dataAsList.set(i, _traverse(key, dataAsList.get(i), processor));
            }
        } else {
            data =  processor.visit(key, data);
        }

        return data;
    }
}
