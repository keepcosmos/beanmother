package io.beanmother.core.converter.std;

import io.beanmother.core.converter.AbstractGenericConverter;

import java.sql.Date;

/**
 * Converter used to convert a String to a sql Date
 */
public class StringToSQLDateConverter extends AbstractGenericConverter<String, Date> {

    private final static StringToDateConverter stringToDateConverter = new StringToDateConverter();
    private final static DateToSQLDateConverter dateToSQLDateConverter = new DateToSQLDateConverter();

    @Override
    public Date convert(String source) {
        return dateToSQLDateConverter.convert(stringToDateConverter.convert(source));
    }
}
