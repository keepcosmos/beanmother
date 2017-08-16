package io.jmother.objectmother;

import io.jmother.core.fixture.parser.FixtureParser;
import io.jmother.core.fixture.parser.YamlFixtureParser;
import io.jmother.core.fixture.scanner.FixtureScanner;
import io.jmother.core.fixture.scanner.YamlFixtureScanner;
import io.jmother.core.mapper.FixtureMapper;
import io.jmother.modelmapper.FixtureModelMapper;

public class ObjectMother {

    private FixtureScanner fixtureScanner;
    private FixtureParser fixtureParser;
    private FixtureMapper fixtureMapper;

    public ObjectMother() {
        this.fixtureScanner = new YamlFixtureScanner(ClassLoader.getSystemClassLoader());
        this.fixtureParser = new YamlFixtureParser();
        this.fixtureMapper = new FixtureModelMapper();
    }

    public <T> T bear(String name, Class<T> targetType){
        return null;
    }

    public <T> T bear(String name, T targetObj) {
        return null;
    }
}
