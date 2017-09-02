package io.beanmother.core.util;

import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.fixture.FixtureValue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link FixtureMapUtil}
 */
@Ignore
public class FixtureMapUtilTest {

    FixtureMap fixtureMap;

    @Before
    public void setup() {
//        fixtureMap = new FixtureMap("test");
//        fixtureMap.put("edge1", 1);
//        fixtureMap.put("edge2", 2);
//        List<Object> arr = new ArrayList<>();
//        arr.add(3);
//        arr.add(4);
//        Map<String, Object> map = new HashMap<>();
//        map.put("edge5", 5);
//        map.put("edge6", 6);
//        arr.add(map);
//        fixtureMap.put("list", arr);
    }

    @Test
    public void testSimpleTraversal() {
        final List<String> allKeys = new ArrayList<>();
        allKeys.add("edge1");
        allKeys.add("edge2");
        allKeys.add("edge5");
        allKeys.add("edge6");
        allKeys.add("list");

        FixtureMapUtil.traverse(fixtureMap, new FixtureMapUtil.Processor() {
            @Override
            public Object visit(String key, Object data) {
                allKeys.remove(key);
                return data;
            }
        });
        assertTrue(allKeys.isEmpty());
    }

    @Test
    public void testTraversalAndUpdateValue() {
        FixtureMapUtil.traverse(fixtureMap, new FixtureMapUtil.Processor() {
            @Override
            public Object visit(String key, Object data) {
                return (Integer) data + 1;
            }
        });
        assertEquals("{edge1=2, edge2=3, list=[4, 5, {edge5=6, edge6=7}]}", fixtureMap.toString());
    }

    @Test
    public void testMapToFixtureMap() {
        Map<String, Object> map = new HashMap();
        map.put("edge1", 1);
        map.put("edge2", 2);
        List<Object> arr = new ArrayList<>();
        arr.add(3);
        arr.add(4);
        Map<String, Object> map2= new HashMap<>();
        map2.put("edge5", 5);
        map2.put("edge6", 6);
        arr.add(map);
        map.put("list", arr);

        FixtureMap fixtureMap = FixtureMapUtil.convertMapToFixtureMap(map);

        assertTrue(fixtureMap.get("edge1") instanceof FixtureValue);
        assertTrue(fixtureMap.get("edge2") instanceof FixtureValue);

    }


}