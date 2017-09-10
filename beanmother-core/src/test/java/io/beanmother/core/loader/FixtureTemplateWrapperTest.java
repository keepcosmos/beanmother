package io.beanmother.core.loader;

import io.beanmother.core.common.FixtureList;
import io.beanmother.core.common.FixtureMap;
import io.beanmother.core.common.FixtureValue;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Test for {@link FixtureTemplateWrapper}
 */
public class FixtureTemplateWrapperTest {

    @Test
    public void testConvertObject() {
        FixtureMap parent = new FixtureMap();

        Date date = new Date();
        FixtureValue value = FixtureTemplateWrapper.wrap(date, "test", parent);

        assertEquals(date, value.getValue());
        assertEquals("test", value.getFixtureName());
        assertEquals(parent, value.getParent());

        String string = "string";

        FixtureValue value2 = FixtureTemplateWrapper.wrap(string, "test", parent);
        assertEquals("string", value2.getValue());
        assertEquals("test", value2.getFixtureName());
        assertEquals(parent, value2.getParent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailConvertValueIsInstanceOfMap() {
        Object data = new HashMap();
        FixtureTemplateWrapper.wrap(data, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailConvertValueIsInstanceOfList() {
        Object data = new ArrayList();
        FixtureTemplateWrapper.wrap(data, null, null);
    }

    @Test
    public void testConvertArray() {
        FixtureMap parent = new FixtureMap();

        List<Object> list = new ArrayList<>();
        String value1 = "string";
        Date value2 = new Date();
        list.add(value1);
        list.add(value2);

        FixtureList fixtureList = FixtureTemplateWrapper.wrap(list, "test", parent);

        assertEquals("test", fixtureList.getFixtureName());
        assertEquals(parent, fixtureList.getParent());

        assertTrue(fixtureList.get(0) instanceof FixtureValue);
        assertTrue(fixtureList.get(1) instanceof FixtureValue);

        FixtureValue fixtureValue1 = (FixtureValue) fixtureList.get(0);
        FixtureValue fixtureValue2 = (FixtureValue) fixtureList.get(1);

        assertEquals(value1, fixtureValue1.getValue());
        assertEquals(value2, fixtureValue2.getValue());

        assertEquals("test", fixtureValue1.getFixtureName());
        assertEquals("test", fixtureValue2.getFixtureName());
    }

    @Test
    public void testConvertMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        String value1 = "string";
        Date value2 = new Date();
        map.put("value1", value1);
        map.put("value2", value2);

        FixtureMap fixtureMap = FixtureTemplateWrapper.wrap(map, "test", null);

        assertEquals("test", fixtureMap.getFixtureName());
        assertNull(fixtureMap.getParent());

        assertTrue(fixtureMap.get("value1") instanceof FixtureValue);
        assertTrue(fixtureMap.get("value2") instanceof FixtureValue);

        FixtureValue fixtureValue1 = (FixtureValue) fixtureMap.get("value1");
        FixtureValue fixtureValue2 = (FixtureValue) fixtureMap.get("value2");

        assertEquals(value1, fixtureValue1.getValue());
        assertEquals(value2, fixtureValue2.getValue());

        assertEquals("value1", fixtureValue1.getFixtureName());
        assertEquals("value2", fixtureValue2.getFixtureName());
    }

    @Test
    public void testComplexMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("date", new Date());
        map.put("string", "string");

        List<Object> list = new ArrayList<>();
        list.add("string-in-list");

        Map<String, Object> mapInList = new LinkedHashMap<>();
        mapInList.put("string-in-map-in-list", "string-in-map-in-list");
        mapInList.put("date-in-map-in-list", new Date());
        list.add(mapInList);

        list.add(new ArrayList<>());

        map.put("list", list);

        FixtureMap fixtureMap = FixtureTemplateWrapper.wrap(map, null, null);

        assertTrue(fixtureMap.get("date") instanceof FixtureValue);
        assertTrue(fixtureMap.get("string") instanceof FixtureValue);
        assertTrue(fixtureMap.get("list") instanceof FixtureList);

        FixtureList fixtureList = (FixtureList) fixtureMap.get("list");
        assertTrue(fixtureList.get(0) instanceof FixtureValue);
        assertTrue(fixtureList.get(1) instanceof FixtureMap);
        assertTrue(fixtureList.get(2) instanceof FixtureList);

        FixtureMap fixtureMapInList = (FixtureMap) fixtureList.get(1);
        assertTrue(fixtureMapInList.get("string-in-map-in-list") instanceof FixtureValue);
        assertTrue(fixtureMapInList.get("date-in-map-in-list") instanceof FixtureValue);
    }
}