package io.beanmother.core.util;

import io.beanmother.core.fixture.FixtureMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test for {@link FixtureMapEdgeTraversal}
 */
public class FixtureMapEdgeTraversalTest {

    FixtureMap fixtureMap;

    @Before
    public void setup() {
        fixtureMap = new FixtureMap("test");
        fixtureMap.put("edge1", 1);
        fixtureMap.put("edge2", 2);
        List<Object> arr = new ArrayList<>();
        arr.add(3);
        arr.add(4);
        Map<String, Object> map = new HashMap<>();
        map.put("edge5", 5);
        map.put("edge6", 6);
        arr.add(map);
        fixtureMap.put("list", arr);
    }

    @Test
    public void testSimpleTraversal() {
        final List<String> allKeys = new ArrayList<>();
        allKeys.add("edge1");
        allKeys.add("edge2");
        allKeys.add("edge5");
        allKeys.add("edge6");
        allKeys.add("list");

        FixtureMapEdgeTraversal.traverse(fixtureMap, new FixtureMapEdgeTraversal.Processor() {
            @Override
            public Object visit(String key, Object data) {
                allKeys.remove(key);
                return data;
            }
        });
        Assert.assertTrue(allKeys.isEmpty());
    }

    @Test
    public void testTraversalAndUpdateValue() {
        FixtureMapEdgeTraversal.traverse(fixtureMap, new FixtureMapEdgeTraversal.Processor() {
            @Override
            public Object visit(String key, Object data) {
                return (Integer) data + 1;
            }
        });
        Assert.assertEquals("{edge1=2, edge2=3, list=[4, 5, {edge5=6, edge6=7}]}", fixtureMap.toString());
    }
}