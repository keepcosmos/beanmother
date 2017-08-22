package io.beanmother.core.mapper;

import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.mapper.converter.Converter;
import io.beanmother.core.mapper.converter.ConverterFactory;

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
        map(fixtureMap, targetObject);
        return targetObject;
    }

    @Override
    public <T> void map(FixtureMap fixtureMap, T targetObject) {
        for (String key : fixtureMap.keySet()) {
            assign(targetObject, key, fixtureMap.get(key));
        }
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
            targetType = getPrimitiveWrapper(targetType);
        }

        Class sourceType = value.getClass();

        if (sourceType.isInstance(Number.class)) {
            sourceType = Number.class;
        }

        Converter converter = new ConverterFactory().get(sourceType, targetType);
        try {
            if (converter != null) {
                targetMethod.invoke(targetObject, converter.convert(value));
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

    private Class<?> getPrimitiveWrapper(final Class<?> type) {
        if (boolean.class.equals(type)) {
            return Boolean.class;
        } else if (float.class.equals(type)) {
            return Float.class;
        } else if (long.class.equals(type)) {
            return Long.class;
        } else if (int.class.equals(type)) {
            return Integer.class;
        } else if (short.class.equals(type)) {
            return Short.class;
        } else if (byte.class.equals(type)) {
            return Byte.class;
        } else if (double.class.equals(type)) {
            return Double.class;
        } else if (char.class.equals(type)) {
            return Character.class;
        } else {
            throw new IllegalArgumentException(type.getName() + " is not supported primitive type");
        }
    }
}
