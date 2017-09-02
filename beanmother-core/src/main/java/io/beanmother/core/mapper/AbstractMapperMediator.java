package io.beanmother.core.mapper;

import io.beanmother.core.converter.ConverterFactory;
import io.beanmother.core.fixture.FixtureList;
import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.fixture.FixtureTemplate;
import io.beanmother.core.fixture.FixtureValue;

import java.util.Map;

public abstract class AbstractMapperMediator implements MapperMediator {

    private ConverterFactory converterFactory;

    public AbstractMapperMediator(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public void map(Object target, FixtureMap fixtureMap) {
        for (Map.Entry<String, FixtureTemplate> entry : fixtureMap.entrySet()) {
            if (entry.getValue() instanceof FixtureMap) {
                getFixtureMapPropertyMapper().map(target, entry.getKey(), (FixtureMap) entry.getValue());
            } else if (entry.getValue() instanceof FixtureList) {
                getFixtureListPropertyMapper().map(target, entry.getKey(), (FixtureList) entry.getValue());
            } else if (entry.getValue() instanceof FixtureValue) {
                getFixtureValuePropertyMapper().map(target, entry.getKey(), (FixtureValue) entry.getValue());
            }
        }
    }

    public ConverterFactory getConverterFactory() {
        return converterFactory;
    }
}
