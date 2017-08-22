package io.beanmother.core.mapper.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.mapper.FixtureMapper;

public class JacksonFixtureMapper implements FixtureMapper {

    private ObjectMapper objectMapper;

    public JacksonFixtureMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> T map(FixtureMap fixtureMap, Class<T> targetType) {
        return null;
    }

    @Override
    public <T> void map(FixtureMap fixtureMap, T targetObject) {
    }
}
