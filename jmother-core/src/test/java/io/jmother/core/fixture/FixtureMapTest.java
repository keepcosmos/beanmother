package io.jmother.core.fixture;

import io.jmother.core.fixture.parser.FixtureFormatException;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Test for {@link FixtureMap}
 */
public class FixtureMapTest {

    @Test
    public void testCreateWithMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("name", "Joshua");
        FixtureMap fixtureMap = new FixtureMap("test", map);

        assertFalse(fixtureMap.isInvalidFormat());
        assertEquals(fixtureMap.getFixtureName(), "test");
        assertEquals(fixtureMap.get("id"), 1);
        assertEquals(fixtureMap.get("name"), "Joshua");
    }

    @Test
    public void testNotParsedFixtureMap() {
        FixtureMap fixtureMap = new FixtureMap("not-parsed", new FixtureFormatException("not-parsed"));
        assertTrue(fixtureMap.isInvalidFormat());
    }

    @Test
    public void testReproduce() {
        FixtureMap origin = new FixtureMap("test");
        Map<String, Object> subMap = new HashMap<>();
        List<Map> list = new ArrayList<>();
        Map<String, Object> mapInList = new HashMap<>();
        Integer integer = new Integer(1);
        Date date = new Date();
        list.add(mapInList);
        origin.put("subMap", subMap);
        origin.put("list", list);
        origin.put("integer", integer);
        origin.put("date", date);


        FixtureMap dup = origin.reproduce();

        assertEquals(origin, dup);
        assertEquals(origin.get("subMap"), dup.get("subMap"));
        assertEquals(origin.get("list"), dup.get("list"));
        assertEquals(dup.get("integer"), integer);
        assertEquals(origin.get("date"), dup.get("date"));

        assertFalse(dup == origin);
        assertFalse(dup.get("subMap") == subMap);
        assertFalse(dup.get("list") == list);
        assertFalse(((List)dup.get("list")).get(0) == mapInList);
        assertFalse(dup.get("integer") == integer);
        assertFalse(dup.get("date") == date);
    }
}