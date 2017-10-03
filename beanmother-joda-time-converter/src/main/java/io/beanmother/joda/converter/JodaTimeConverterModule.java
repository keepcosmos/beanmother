package io.beanmother.joda.converter;

import io.beanmother.core.converter.Converter;
import io.beanmother.core.converter.ConverterModule;

import java.util.HashSet;
import java.util.Set;

/**
 * Joda-Time converter module.
 */
public class JodaTimeConverterModule implements ConverterModule {

    private final static Set<Converter> converters;

    static {
        converters = new HashSet<>();
        converters.add(new DateToJodaTimeBaseLocalConverter());
        converters.add(new StringToJodaTimeBaseLocalConverter());
        converters.add(new JodaTimeSingleFieldPeriodConverter());
    }

    @Override
    public Set<Converter> getConverters() {
        return converters;
    }
}
