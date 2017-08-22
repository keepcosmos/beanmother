package io.beanmother.core.mapper.converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ConverterFactory {

    private Map<String, Converter> converters = new HashMap<>();

    public ConverterFactory() {
        register(new NumberToIntegerConverter());
    }

    public void register(Converter converter) {
        converters.put(buildKey(converter), converter);
    }

    public Converter get(Class<?> sourceType, Class<?> destType) {
        return converters.get(buildKey(sourceType, destType));
    }

    private String buildKey(Class<?> sourceType, Class<?> destType) {
        return sourceType.toString() + "-" + destType.toString();
    }

    private String buildKey(Converter converter) {
        Type[] types = converter.getClass().getGenericInterfaces();
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType ptype = (ParameterizedType) type;
                if(ptype.getRawType().equals(Converter.class)) {
                    Type[] actual = ptype.getActualTypeArguments();
                    return buildKey((Class<?>) actual[0], (Class<?>) actual[1]);
                }
            }
        }
        throw new IllegalArgumentException(converter.toString() + " is not suitable type");
    }

}
