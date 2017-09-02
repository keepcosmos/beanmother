package io.beanmother.core.converter;

import io.beanmother.core.converter.std.DateToSQLDateConverter;
import io.beanmother.core.converter.std.NumberToNumberConverter;
import io.beanmother.core.converter.std.SameClassConverter;
import io.beanmother.core.converter.std.StringToNumberConverter;

public class StandardConverterFactory extends ConverterFactory {

    private static Converter[] standardConverters;

    static {
        standardConverters = new Converter[]{
            new SameClassConverter(Integer.MAX_VALUE),
            new NumberToNumberConverter(),
            new StringToNumberConverter(),
            new DateToSQLDateConverter()
        };
    }

    public StandardConverterFactory() {
        super(standardConverters);
    }
}
