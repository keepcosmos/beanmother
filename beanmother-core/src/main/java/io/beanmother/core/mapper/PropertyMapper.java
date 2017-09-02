package io.beanmother.core.mapper;

import io.beanmother.core.fixture.FixtureTemplate;

public interface PropertyMapper<T extends FixtureTemplate> {
    void map(Object target, String key, T value);
}
