package io.beanmother.core.converter;

import io.beanmother.core.converter.std.*;

public class StandardConverterFactory extends ConverterFactory {

    private static Converter[] standardConverters;

    static {
        standardConverters = new Converter[]{
                new SameClassConverter(Integer.MAX_VALUE),
                new NumberToNumberConverter(),
                new NumberToStringConverter(),
                new StringToNumberConverter(),
                new StringToDateConverter(),
                new DateToSQLDateConverter(),
                new StringToEnumConverter()
        };
    }

    public StandardConverterFactory() {
        super(standardConverters);
    }
}
