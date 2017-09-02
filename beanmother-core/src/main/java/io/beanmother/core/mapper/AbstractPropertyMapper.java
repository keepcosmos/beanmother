package io.beanmother.core.mapper;

import io.beanmother.core.converter.ConverterFactory;
import io.beanmother.core.fixture.FixtureTemplate;

public abstract class AbstractPropertyMapper<T extends FixtureTemplate> implements PropertyMapper<T> {

    private ConverterFactory converterFactory;

    public AbstractPropertyMapper(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    public ConverterFactory getConverterFactory() {
        return converterFactory;
    }
}
