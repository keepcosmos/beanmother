package io.beanmother.core.mapper;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.common.FixtureList;
import io.beanmother.core.common.FixtureMap;
import io.beanmother.core.common.FixtureTemplate;
import io.beanmother.core.common.FixtureValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class FieldFixtureMapper extends AbstractFixtureMapper {
    private final static Logger logger = LoggerFactory.getLogger(FieldFixtureMapper.class);

    /**
     * Create a implementation of FixtureMapper
     *
     * @param mapperMediator
     */
    public FieldFixtureMapper(MapperMediator mapperMediator) {
        super(mapperMediator);
    }

    @Override
    protected void bind(Object target, String key, FixtureMap fixtureMap) {
        _bind(target, key, fixtureMap);
    }

    @Override
    protected void bind(Object target, String key, FixtureList fixtureList) {
        _bind(target, key, fixtureList);
    }

    @Override
    protected void bind(Object target, String key, FixtureValue fixtureValue) {
        _bind(target, key, fixtureValue);
    }

    private void _bind(Object target, String key, FixtureTemplate template) {
        Field field = findField(target.getClass(), key);
        if (field == null) return;

        TypeToken<?> targetType = TypeToken.of(field.getType());
        Object value = getFixtureConverter().convert(template, targetType);
        if (value == null) return;

        try {
            field.set(target, value);
        } catch (IllegalAccessException e) {
            throw new FixtureMappingException(e);
        }
    }

    private Field findField(Class<?> targetClass, String key) {
        try {
            return targetClass.getDeclaredField(key);
        } catch (NoSuchFieldException e) {
            Class<?> superClass = targetClass.getSuperclass();
            if (superClass == null || superClass == Object.class) {
                return null;
            }
            return findField(targetClass.getSuperclass(), key);
        }
    }
}
