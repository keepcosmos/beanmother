package io.beanmother.core.mapper.setter;

import io.beanmother.core.converter.ConverterFactory;
import io.beanmother.core.fixture.FixtureList;
import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.fixture.FixtureValue;
import io.beanmother.core.mapper.AbstractMapperMediator;
import io.beanmother.core.mapper.PropertyMapper;

/**
 * Mapping property value by setter. Also support add prefix method if exists for array
 */
public class SetterMapperMediator extends AbstractMapperMediator {

    private PropertyMapper<FixtureValue> fixtureValuePropertyMapper;
    private PropertyMapper<FixtureList> fixtureListPropertyMapper;
    private PropertyMapper<FixtureMap> fixtureMapPropertyMapper;

    public SetterMapperMediator(ConverterFactory converterFactory) {
        super(converterFactory);
        fixtureValuePropertyMapper = new FixtureValueSetterMapper(this);
        fixtureListPropertyMapper = new FixtureListSetterMapper(this);
        fixtureMapPropertyMapper = new FixtureMapSetterMapper(this);
    }

    @Override
    public PropertyMapper<FixtureValue> getFixtureValuePropertyMapper() {
        return fixtureValuePropertyMapper;
    }

    @Override
    public PropertyMapper<FixtureList> getFixtureListPropertyMapper() {
        return fixtureListPropertyMapper;
    }

    @Override
    public PropertyMapper<FixtureMap> getFixtureMapPropertyMapper() {
        return fixtureMapPropertyMapper;
    }
}
