package io.beanmother.core.converter.std;

import io.beanmother.core.converter.AbstractGenericConverter;

import java.util.Date;

public class DateToSQLDateConverter extends AbstractGenericConverter<Date, java.sql.Date> {
    @Override
    protected java.sql.Date convert(Date source) {
        return new java.sql.Date(source.getTime());
    }
}
