package io.beanmother.core.mapper.converter;

import io.beanmother.core.mapper.converter.std.DateToSQLDateConverter;
import io.beanmother.core.mapper.converter.std.NumberToNumberConverter;
import io.beanmother.core.mapper.converter.std.SameClassConverter;

public class StandardConverterFactory extends ConverterFactory {

    private static Converter[] standardConverters;

    static {
        standardConverters = new Converter[]{
            new SameClassConverter(Integer.MAX_VALUE),
            new NumberToNumberConverter(),
            new DateToSQLDateConverter()
        };
    }

    public StandardConverterFactory() {
        super(standardConverters);
    }
}
