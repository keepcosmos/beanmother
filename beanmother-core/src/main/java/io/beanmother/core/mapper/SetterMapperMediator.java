package io.beanmother.core.mapper;

import io.beanmother.core.converter.ConverterFactory;

/**
 * The implementation of {@link MapperMediator}
 */
public class SetterMapperMediator implements MapperMediator {

    private FixtureMapper fixtureMapper;

    private FixtureConverter fixtureConverter;

    /**
     * Create a SetterMapperMediator.
     * @param converterFactory
     */
    public SetterMapperMediator(ConverterFactory converterFactory) {
        this.fixtureConverter = new FixtureConverterImpl(this, converterFactory);
        this.fixtureMapper = new SetterFixtureMapper(this);
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
