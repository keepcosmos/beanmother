package io.beanmother.core.mapper;

import io.beanmother.core.converter.ConverterFactory;
import io.beanmother.core.common.FixtureMap;

/**
 * A DefaultFixtureMapper.
 */
public class DefaultFixtureMapper implements FixtureMapper, MapperMediator {

    private MapperMediator mapperMediator;

    public DefaultFixtureMapper(ConverterFactory converterFactory) {
        this.mapperMediator = new MapperMediatorImpl(converterFactory);
    }

    @Override
    public <T> T map(FixtureMap fixtureMap, Class<T> targetType) {
        return getFixtureMapper().map(fixtureMap, targetType);
    }

    @Override
    public void map(FixtureMap fixtureMap, Object target) {
        getFixtureMapper().map(fixtureMap, target);
    }

    @Override
    public void setSetterPrefix(String setterPrefix) {
        getFixtureMapper().setSetterPrefix(setterPrefix);
    }

    @Override
    public FixtureMapper getFixtureMapper() {
        return mapperMediator.getFixtureMapper();
    }

    @Override
    public FixtureConverter getFixtureConverter() {
        return mapperMediator.getFixtureConverter();
    }
}
