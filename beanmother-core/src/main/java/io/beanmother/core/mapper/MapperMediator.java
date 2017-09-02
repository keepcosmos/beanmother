package io.beanmother.core.mapper;

import io.beanmother.core.fixture.FixtureList;
import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.fixture.FixtureValue;

public interface MapperMediator {
    void map(Object target, FixtureMap fixtureMap);

    PropertyMapper<FixtureValue> getFixtureValuePropertyMapper();
    PropertyMapper<FixtureList> getFixtureListPropertyMapper();
    PropertyMapper<FixtureMap> getFixtureMapPropertyMapper();
}
