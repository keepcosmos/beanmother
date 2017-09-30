package io.beanmother.joda.converter;

import io.beanmother.core.converter.Converter;
import io.beanmother.core.converter.ConverterModule;
import io.beanmother.core.converter.std.StringToDateConverter;

import java.util.HashSet;
import java.util.Set;

public class JodaTimeConverterModule implements ConverterModule {

    private final static StringToDateConverter stringToDateConverter = new StringToDateConverter();

    private final static Set<Converter> converters;

    static {
        converters = new HashSet<>();
        converters.add(null);
    }

    @Override
    public Set<Converter> getConverters() {
        return converters;
    }


}
