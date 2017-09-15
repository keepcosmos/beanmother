package io.beanmother.core.mapper;

import io.beanmother.core.converter.ConverterFactory;

public class FieldMapperMediator implements MapperMediator {

    private FixtureMapper fixtureMapper;

    private FixtureConverter fixtureConverter;

    /**
     * Create a SetterMapperMediator.
     * @param converterFactory
     */
    public FieldMapperMediator(ConverterFactory converterFactory) {
        this.fixtureConverter = new FixtureConverterImpl(this, converterFactory);
        this.fixtureMapper = new FieldFixtureMapper(this);
    }
    @Override
    public FixtureMapper getFixtureMapper() {
        return fixtureMapper;
    }

    @Override
    public FixtureConverter getFixtureConverter() {
        return fixtureConverter;
    }
}
