package io.beanmother.core.mapper.converter;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.mapper.converter.std.SameClassConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConverterFactory {
    private static Converter sameClassConverter = new SameClassConverter();

    private List<Converter> converters = new ArrayList<>();

    public ConverterFactory() { }

    public ConverterFactory(Converter... converters) {
        for (Converter converter : converters) {
            this.converters.add(converter);
            Collections.sort(this.converters);
        }
    }

    public void register(Converter converter) {
        converters.add(converter);
        Collections.sort(this.converters);
    }

    public Converter get(Object source, TypeToken<?> targetTokenType) {
        for (Converter converter : converters) {
            if (converter.canHandle(source, targetTokenType)) {
                return converter;
            }
        }

        if (sameClassConverter.canHandle(source, targetTokenType)) {
            return sameClassConverter;
        }

        return null;
    }
}
