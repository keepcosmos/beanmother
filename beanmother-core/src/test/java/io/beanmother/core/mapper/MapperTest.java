package io.beanmother.core.mapper;

import io.beanmother.core.fixture.store.DefaultFixturesStore;
import io.beanmother.core.fixture.store.FixturesStore;
import io.beanmother.core.util.Location;

import java.io.IOException;

public class MapperTest {

    FixturesStore fixturesStore;

    public FixturesStore getFixturesStore() throws IOException {
        if (fixturesStore == null) {
            fixturesStore = new DefaultFixturesStore();
            fixturesStore.addLocation(new Location("testmodel_fixtures"));
        }
        return fixturesStore;
    }
}
