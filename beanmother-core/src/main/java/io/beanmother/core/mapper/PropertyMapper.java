package io.beanmother.core.mapper;

import io.beanmother.core.fixture.FixtureTemplate;

public interface PropertyMapper {
    void map(Object target, String key, FixtureTemplate value);
}
