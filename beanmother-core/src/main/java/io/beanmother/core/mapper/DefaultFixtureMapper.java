package io.beanmother.core.mapper;

import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.converter.Converter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DefaultFixtureMapper implements FixtureMapper {

    @Override
    public <T> T map(FixtureMap fixtureMap, Class<T> targetType) {
        Constructor constructor = null;
        T targetObject;
        try {
            constructor = targetType.getConstructor();
            targetObject = (T) constructor.newInstance();
        } catch (Exception e) {
            throw new FixtureMappingException(e);
        }
        return targetObject;
    }

    private void assign(Object targetObject, String key, Object value) {
        if (targetObject == null || key == null || key.isEmpty() || value == null) {
            return;
        }

        Method targetMethod = null;

        for (Method method : targetObject.getClass().getMethods()) {
            if (method.getName().replaceFirst("set", "").equalsIgnoreCase(key)) {
                targetMethod = method;
                break;
            }
        }

        if (targetMethod == null) return;

        Class<?>[] targetTypes = targetMethod.getParameterTypes();

        if (targetTypes.length != 1) {
            throw new IllegalArgumentException("multiple parameter types");
        }

        Class targetType = targetTypes[0];

        if (targetType.isPrimitive()) {
//            targetType = getPrimitiveWrapper(targetType);
        }

        Class sourceType = value.getClass();

        if (sourceType.isInstance(Number.class)) {
            sourceType = Number.class;
        }

        Converter converter = null;
        try {
            if (converter != null) {
//                targetMethod.invoke(targetObject, converter.convert(value));
            } else if (sourceType.equals(targetType)) {
                targetMethod.invoke(targetObject, value);
            } else {

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
