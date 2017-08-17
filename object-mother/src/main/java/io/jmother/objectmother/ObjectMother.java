package io.beanmother.objectmother;

import io.beanmother.core.fixture.parser.FixtureParser;
import io.beanmother.core.fixture.parser.YamlFixtureParser;
import io.beanmother.core.fixture.scanner.FixtureScanner;
import io.beanmother.core.fixture.scanner.YamlFixtureScanner;
import io.beanmother.core.mapper.FixtureMapper;
import io.beanmother.modelmapper.FixtureModelMapper;

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
