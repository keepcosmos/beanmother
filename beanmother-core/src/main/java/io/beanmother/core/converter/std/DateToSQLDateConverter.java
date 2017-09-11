package io.beanmother.core.converter.std;

import io.beanmother.core.converter.AbstractGenericConverter;

import java.util.Date;

/**
 * Converter used to convert a Date to a sql Date
 */
public class DateToSQLDateConverter extends AbstractGenericConverter<Date, java.sql.Date> {
    @Override
    public java.sql.Date convert(Date source) {
        return new java.sql.Date(source.getTime());
    }
}
