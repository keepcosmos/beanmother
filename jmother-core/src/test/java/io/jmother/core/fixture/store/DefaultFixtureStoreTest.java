package io.jmother.core.fixture.store;

import io.jmother.core.util.Location;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link DefaultFixtureStore}
 */
public class DefaultFixtureStoreTest {

    DefaultFixtureStore fixtureStore;

    @Before
    public void setup() {
        fixtureStore = new DefaultFixtureStore();
    }

    @Test
    public void testAddLocation() {
        String fixturePath = "fixtures/animals/pets";
        fixtureStore.addLocation(new Location(fixturePath));

        assertTrue(fixtureStore.getFixtureLocations().contains(new Location(fixturePath)));

        List<String> fileNames = new ArrayList<>();
        for(File file : fixtureStore.getFixtureFiles()) {
            fileNames.add(file.getName());
        }

        assertTrue(fileNames.contains("cat.yml"));
        assertTrue(fileNames.contains("dog.yml"));
    }

    @Test
    public void testRefresh() {
        fixtureStore.addLocation(new Location("fixtures/animals/pets"));
        fixtureStore.refresh();

        Set<String> keys = fixtureStore.getFixtureMap().keySet();

        assertTrue(keys.contains("davi"));
        assertTrue(keys.contains("rooney"));
        assertTrue(keys.contains("ruru"));

        assertFalse(keys.contains("tiger"));  // tiger is not in pets/

        // add location and refresh
        fixtureStore.addLocation(new Location("fixtures/animals"));
        fixtureStore.refresh();
        keys = fixtureStore.getFixtureMap().keySet();
        assertTrue(keys.contains("davi"));
        assertTrue(keys.contains("tiger"));
    }

    @Test
    public void testReset() {
        fixtureStore.addLocation(new Location("fixtures/animals/pets"));
        fixtureStore.refresh();

        fixtureStore.reset();

        assertTrue(fixtureStore.getFixtureLocations().isEmpty());
        assertTrue(fixtureStore.getFixtureFiles().isEmpty());
        assertTrue(fixtureStore.getFixtureMap().isEmpty());
    }

    @Test
    public void testCopyMap() {
        Map<String, Object> map = new HashMap<>();

        Map<String, Object> subMap1 = new HashMap<>();
        List<Map> list = new ArrayList<>();
        Map<String, Object> subMap2 = new HashMap<>();

        list.add(subMap2);
        map.put("map", subMap1);
        map.put("list", list);
        map.put("string", "string");
        map.put("integer", new Integer(1));

        Map<String, Object> dupMap =new HashMap<>(map);

        Assert.assertTrue(dupMap.equals(map));
    }


}