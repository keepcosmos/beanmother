package io.beanmother.core.common;

import io.beanmother.core.common.FixtureMap;
import io.beanmother.core.common.FixtureMapTraversal;
import io.beanmother.core.common.FixtureValue;
import io.beanmother.core.loader.FixtureTemplateWrapper;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link FixtureMapTraversal}
 */
public class FixtureMapTraversalTest {

    FixtureMap sampleFixture;

    @Before
    public void setup() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("edge1", 1);
        map.put("edge2", 2);
        List<Object> arr = new ArrayList<>();
        arr.add(3);
        arr.add(4);
        Map<String, Object> nestedMap = new LinkedHashMap<>();
        nestedMap.put("edge5", 5);
        nestedMap.put("edge6", 6);
        arr.add(nestedMap);
        map.put("list", arr);

        sampleFixture = FixtureTemplateWrapper.wrap(map, "test", null);
    }

    @Test
    public void testSimpleTraversal() {
        final List<String> allKeys = new ArrayList<>();
        allKeys.add("edge1");
        allKeys.add("edge2");
        allKeys.add("edge5");
        allKeys.add("edge6");
        allKeys.add("list");
        Map<String, Object> map = new HashMap<>();
        map.put("list", allKeys);

        FixtureMap fixture = FixtureTemplateWrapper.wrap(map, null, null);

        FixtureMapTraversal.traverse(fixture, new FixtureMapTraversal.Processor() {
            @Override
            public void visit(FixtureValue edge) {
                allKeys.remove(edge.getValue());
            }
        });

        assertTrue(allKeys.isEmpty());
    }

    @Test
    public void testTraversalAndUpdateValue() {
        FixtureMapTraversal.traverse(sampleFixture, new FixtureMapTraversal.Processor() {
            @Override
            public void visit(FixtureValue edge) {
                edge.setValue((Integer) edge.getValue() + 1);
            }
        });
        assertEquals("{edge1=2, edge2=3, list=[4, 5, {edge5=6, edge6=7}]}", sampleFixture.toString());
    }

}