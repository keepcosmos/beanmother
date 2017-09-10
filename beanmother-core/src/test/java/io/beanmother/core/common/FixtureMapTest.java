package io.beanmother.core.common;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test for {@link FixtureMap}
 */
public class FixtureMapTest {

    @Test
    public void testReproduce() {
        FixtureMap origin = new FixtureMap();
        FixtureMap subMap = new FixtureMap();
        FixtureList list = new FixtureList();
        FixtureMap mapInList = new FixtureMap();

        FixtureValue integer = new FixtureValue(1);
        FixtureValue date = new FixtureValue(new Date());
        list.add(mapInList);
        origin.put("subMap", subMap);
        origin.put("list", list);
        origin.put("integer", integer);
        origin.put("date", date);

        origin.setFixtureName("origin-fixture-name");
        origin.setRoot(true);

        FixtureMap dup = origin.reproduce();

        assertEquals(origin, dup);
        assertEquals(origin.get("subMap"), dup.get("subMap"));
        assertEquals(origin.get("list"), dup.get("list"));
        assertEquals(dup.get("integer"), integer);
        assertEquals(origin.get("date"), dup.get("date"));

        assertFalse(dup == origin);
        assertFalse(dup.get("subMap") == subMap);
        assertFalse(dup.get("list") == list);
        assertFalse(((FixtureList)dup.get("list")).get(0) == mapInList);
        assertFalse(dup.get("integer") == integer);
        assertFalse(dup.get("date") == date);
    }
}