package io.beanmother.core.converter.std;

import io.beanmother.core.converter.AbstractGenericConverter;

import java.util.Calendar;

/**
 * Converter used to convert a String to a Calendar
 */
public class StringToCalendarConverter extends AbstractGenericConverter<String, Calendar> {

    private static final StringToDateConverter stringToDateConverter = new StringToDateConverter();
    private static final DateToCalendarConverter dateToCalendarConverter = new DateToCalendarConverter();

    @Override
    public Calendar convert(String source) {
        return dateToCalendarConverter.convert(stringToDateConverter.convert(source));
    }
}
