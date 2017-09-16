package io.beanmother.core.converter.std;

import io.beanmother.core.converter.ConverterException;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link StringToDateConverter}
 */
public class StringToDateConverterTest {
    StringToDateConverter converter = new StringToDateConverter();

    @Test
    public void testSimpleDateFormat() {
        Date date = (Date) converter.convert("2017-01-02");
        assertEquals(2017, getCalFiled(date, Calendar.YEAR));
        assertEquals(Calendar.JANUARY, getCalFiled(date, Calendar.MONTH));
        assertEquals(2, getCalFiled(date, Calendar.DAY_OF_MONTH));

        date = (Date) converter.convert("01/02/2017");
        assertEquals(2017, getCalFiled(date, Calendar.YEAR));
        assertEquals(Calendar.JANUARY, getCalFiled(date, Calendar.MONTH));
        assertEquals(2, getCalFiled(date, Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testTestDateFromat() {
        Date now = new Date();
        Date date = (Date) converter.convert("now");

        assertEquals(getCalFiled(now, Calendar.YEAR), getCalFiled(date, Calendar.YEAR));
        assertEquals(getCalFiled(now, Calendar.MONTH), getCalFiled(date, Calendar.MONTH));
        assertEquals(getCalFiled(now, Calendar.DAY_OF_MONTH), getCalFiled(date, Calendar.DAY_OF_MONTH));

        date = (Date) converter.convert("Friday, September 8, 2017");
        assertEquals(2017, getCalFiled(date, Calendar.YEAR));
        assertEquals(Calendar.SEPTEMBER, getCalFiled(date, Calendar.MONTH));
        assertEquals(8, getCalFiled(date, Calendar.DAY_OF_MONTH));
    }

    @Test(expected = ConverterException.class)
    public void testRaiseException() {
        converter.convert("hi there");
    }

    private int getCalFiled(Date date, int calendarUnit) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(calendarUnit);
    }
}