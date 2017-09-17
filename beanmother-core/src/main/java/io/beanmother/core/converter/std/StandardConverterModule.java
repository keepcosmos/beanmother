package io.beanmother.core.converter.std;

import io.beanmother.core.converter.Converter;
import io.beanmother.core.converter.ConverterModule;

import java.util.HashSet;
import java.util.Set;

public class StandardConverterModule implements ConverterModule {

    private static final Set<Converter> standardConverters = new HashSet<>();

    static {
        standardConverters.add(new SameClassConverter(Integer.MAX_VALUE));
        standardConverters.add(new ObjectToStringConverter(Integer.MAX_VALUE));
        standardConverters.add(new NumberToNumberConverter());
        standardConverters.add(new StringToNumberConverter());
        standardConverters.add(new StringToDateConverter());
        standardConverters.add(new StringToSQLDateConverter());
        standardConverters.add(new StringToCalendarConverter());
        standardConverters.add(new StringToFileConverter());
        standardConverters.add(new StringToURIConverter());
        standardConverters.add(new StringToURLConverter());
        standardConverters.add(new DateToSQLDateConverter());
        standardConverters.add(new DateToCalendarConverter());
        standardConverters.add(new StringToEnumConverter());
    }


    @Override
    public Set<Converter> getConverters() {
        return standardConverters;
    }
}
