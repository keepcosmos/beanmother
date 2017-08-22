package io.beanmother.core;

import io.beanmother.core.fixture.parser.FixtureParser;
import io.beanmother.core.fixture.parser.YamlFixtureParser;
import io.beanmother.core.fixture.scanner.FixtureScanner;
import io.beanmother.core.fixture.scanner.YamlFixtureScanner;
import io.beanmother.core.mapper.DefaultFixtureMapper;
import io.beanmother.core.mapper.FixtureMapper;

public class ObjectMother {
    private FixtureScanner fixtureScanner;
    private FixtureParser fixtureParser;
    private FixtureMapper fixtureMapper;

    public ObjectMother() {
        this.fixtureScanner = new YamlFixtureScanner(ClassLoader.getSystemClassLoader());
        this.fixtureParser = new YamlFixtureParser();
        this.fixtureMapper = new DefaultFixtureMapper();
    }

    public <T> T bear(String name, Class<T> targetType){
        return null;
    }

    public <T> T bear(String name, T targetObj) {
        return null;
    }
}
