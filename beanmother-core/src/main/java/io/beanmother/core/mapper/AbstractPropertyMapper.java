package io.beanmother.core.mapper;

import io.beanmother.core.mapper.converter.ConverterFactory;

public abstract class AbstractPropertyMapper implements PropertyMapper {
    private ConverterFactory converterFactory;

    public AbstractPropertyMapper(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    public ConverterFactory getConverterFactory() {
        return converterFactory;
    }
}
