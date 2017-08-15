package io.jmother;

import io.jmother.core.fixture.scanner.FixtureScanner;
import io.jmother.core.mapper.FixtureMapper;
import io.jmother.modelmapper.FixtureModelMapper;

public class ObjectMother {
    private FixtureMapper fixtureMapper = new FixtureModelMapper();

    private FixtureScanner fileScanner;

    public static ObjectMother build(String fixtureName) {
        return null;
    }
}
