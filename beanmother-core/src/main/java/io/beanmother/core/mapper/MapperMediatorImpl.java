package io.beanmother.core.mapper;

import io.beanmother.core.converter.ConverterFactory;

/**
 * The implementation of {@link MapperMediator}
 */
public class MapperMediatorImpl implements MapperMediator {

    private FixtureMapper fixtureMapper;

    private FixtureConverter fixtureConverter;

    /**
     * Create a MapperMediatorImpl.
     * @param converterFactory
     */
    public MapperMediatorImpl(ConverterFactory converterFactory) {
        this.fixtureConverter = new FixtureConverterImpl(this, converterFactory);
        this.fixtureMapper = new SetterAndFieldFixtureMapper(this);
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
