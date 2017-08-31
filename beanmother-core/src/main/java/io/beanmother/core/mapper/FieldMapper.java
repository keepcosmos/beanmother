package io.beanmother.core.mapper;

import io.beanmother.core.fixture.FixtureTemplate;
import io.beanmother.core.converter.ConverterFactory;

public class FieldMapper extends AbstractPropertyMapper {

    public FieldMapper(ConverterFactory converterFactory) {
        super(converterFactory);
    }

    @Override
    public void map(Object target, String key, FixtureTemplate value) {

    }
}
