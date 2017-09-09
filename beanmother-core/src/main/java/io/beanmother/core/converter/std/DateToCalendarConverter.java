package io.beanmother.core.converter.std;

import io.beanmother.core.converter.AbstractGenericConverter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Converter used to convert a Date to a Calendar.
 */
public class DateToCalendarConverter extends AbstractGenericConverter<Date, Calendar> {
    @Override
    protected Calendar convert(Date source) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(source);
        return cal;
    }
}
