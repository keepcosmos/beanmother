package io.beanmother.core.mapper;

import io.beanmother.core.common.*;

import java.util.Map;

/**
 * AbstractFixtureMapper is a abstract implementation of {@link FixtureMapper}.
 * It maps target object properties from FixtureTemplate.
 */
public abstract class AbstractFixtureMapper implements FixtureMapper {

    private MapperMediator mapperMediator;

    /**
     * Create a implementation of FixtureMapper
     * @param mapperMediator
     */
    AbstractFixtureMapper(MapperMediator mapperMediator) {
        this.mapperMediator = mapperMediator;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T map(FixtureMap fixtureMap, Class<T> targetType) {
        try {
            T target = (T) ConstructHelper.construct(targetType, fixtureMap);
            map(fixtureMap, target);
            return target;
        } catch (FixtureMappingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void map(FixtureMap fixtureMap, Object target) {
        for (Map.Entry<String, FixtureTemplate> entry : fixtureMap.entrySet()) {
            map(target, entry.getKey(), entry.getValue());
        }
    }

    /**
     * Set a value of the key to target object.
     * @param target
     * @param key
     * @param fixtureTemplate
     */
    public void map(final Object target, final String key, FixtureTemplate fixtureTemplate) {
        new FixtureTemplateSubTypeHandler() {
            @Override
            protected void handleIf(FixtureMap fixtureMap) {
                bind(target, key, fixtureMap);
            }

            @Override
            protected void handleIf(FixtureList fixtureList) {
                bind(target, key, fixtureList);
            }

            @Override
            protected void handleIf(FixtureValue fixtureValue) {
                bind(target, key, fixtureValue);
            }
        }.handle(fixtureTemplate);
    }

    public FixtureConverter getFixtureConverter() {
        return mapperMediator.getFixtureConverter();
    }

    protected abstract void bind(Object target, String key, FixtureMap fixtureMap);

    protected abstract void bind(Object target, String key, FixtureList fixtureList);

    protected abstract void bind(Object target, String key, FixtureValue fixtureValue);

}
