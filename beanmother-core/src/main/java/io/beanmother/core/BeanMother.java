package io.beanmother.core;

import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.fixture.parser.FixtureParser;
import io.beanmother.core.fixture.parser.YamlFixtureParser;
import io.beanmother.core.fixture.scanner.FixtureScanner;
import io.beanmother.core.fixture.scanner.YamlFixtureScanner;
import io.beanmother.core.fixture.store.DefaultFixturesStore;
import io.beanmother.core.fixture.store.FixturesStore;
import io.beanmother.core.mapper.DefaultFixtureMapper;
import io.beanmother.core.mapper.FixtureMapper;
import io.beanmother.core.mapper.postprocessor.FixtureMappingPostProcessor;
import io.beanmother.core.mapper.preprocessor.FixtureMappingPreProcessor;
import io.beanmother.core.util.ClassUtils;
import io.beanmother.core.util.Location;

import java.io.IOException;

public class BeanMother {
    private FixtureMapper fixtureMapper;
    private FixturesStore fixturesStore;

    public BeanMother(FixtureScanner fixtureScanner, FixtureParser fixtureParser, FixtureMapper fixtureMapper) {
        this.fixturesStore = new DefaultFixturesStore(fixtureScanner, fixtureParser);
        this.fixtureMapper = fixtureMapper;
    }

    public BeanMother() {
        FixtureScanner fixtureScanner = new YamlFixtureScanner(ClassUtils.getDefaultClassLoader());
        FixtureParser fixtureParser = new YamlFixtureParser();
        this.fixturesStore = new DefaultFixturesStore(fixtureScanner, fixtureParser);
        this.fixtureMapper = new DefaultFixtureMapper();
    }

    public void addLoadtion(Location location) throws IOException {
        fixturesStore.addLocation(location);
    }

    public void registerPreProcessor(FixtureMappingPreProcessor preprocessor) {

    }

    public void registerPostProcessor(FixtureMappingPostProcessor processor) {

    }

    public <T> T bear(String fixtureName, Class<T> targetType) throws IllegalAccessException, InstantiationException {
        T targetObj = targetType.newInstance();
        bear(fixtureName, targetObj);
        return targetObj;
    }

    public <T> void bear(String fixture, T targetObj) {
        FixtureMap fixtureMap = fixturesStore.get(fixture);
    }
}
