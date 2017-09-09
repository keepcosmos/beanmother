package io.beanmother.core.converter;

import io.beanmother.core.converter.std.*;

public class StandardConverterFactory extends ConverterFactory {

    private static Converter[] standardConverters;

    static {
        standardConverters = new Converter[]{
                new SameClassConverter(Integer.MAX_VALUE),
                new ObjectToStringConverter(Integer.MAX_VALUE),
                new NumberToNumberConverter(),
                new StringToNumberConverter(),
                new StringToDateConverter(),
                new StringToSQLDateConverter(),
                new StringToCalendarConverter(),
                new StringToFileConverter(),
                new StringToURIConverter(),
                new StringToURLConverter(),
                new DateToSQLDateConverter(),
                new DateToCalendarConverter(),
                new StringToEnumConverter()
        };
    }

    public StandardConverterFactory() {
        super(standardConverters);
    }
}
