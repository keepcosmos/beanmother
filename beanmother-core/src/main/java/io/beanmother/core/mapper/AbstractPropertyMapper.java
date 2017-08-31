package io.beanmother.core.mapper;

import io.beanmother.core.converter.ConverterFactory;

public abstract class AbstractPropertyMapper implements PropertyMapper {
    private ConverterFactory converterFactory;

    public AbstractPropertyMapper(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    public ConverterFactory getConverterFactory() {
        return converterFactory;
    }

    protected Class<?> getPrimitiveWrapper(final Class<?> type) {
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
