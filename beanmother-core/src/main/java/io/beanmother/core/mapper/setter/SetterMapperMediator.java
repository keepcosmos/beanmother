package io.beanmother.core.mapper.setter;

import io.beanmother.core.converter.ConverterFactory;
import io.beanmother.core.fixture.FixtureList;
import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.fixture.FixtureTemplate;
import io.beanmother.core.fixture.FixtureValue;
import io.beanmother.core.mapper.MapperMediator;
import io.beanmother.core.mapper.PropertyMapper;

/**
 * Mapping property value by setter. Also support add prefix method if exists for array
 */
public class SetterMapperMediator implements MapperMediator {

    private PropertyMapper<FixtureValue> fixtureValuePropertyMapper;
    private PropertyMapper<FixtureList> fixtureListPropertyMapper;
    private PropertyMapper<FixtureMap> fixtureMapPropertyMapper;

    private ConverterFactory converterFactory;

    public SetterMapperMediator(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
        fixtureValuePropertyMapper = new FixtureValueSetterMapper(this);
        fixtureListPropertyMapper = new FixtureListSetterMapper(this);
        fixtureMapPropertyMapper = new FixtureMapSetterMapper(this);
    }

    public void map(Object target, FixtureTemplate fixtureTemplate) {

    }

    public ConverterFactory getConverterFactory() {
         return converterFactory;
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
