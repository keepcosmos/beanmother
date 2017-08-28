package io.beanmother.core.mapper.converter.std;

import io.beanmother.core.mapper.converter.AbstractGenericConverter;

import java.util.Date;

public class DateToSQLDateConverter extends AbstractGenericConverter<Date, java.sql.Date> {
    @Override
    protected java.sql.Date doConvert(Date source) {
        return new java.sql.Date(source.getTime());
    }
}
