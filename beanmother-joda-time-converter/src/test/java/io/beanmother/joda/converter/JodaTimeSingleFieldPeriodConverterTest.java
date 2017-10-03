package io.beanmother.joda.converter;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.ConverterException;
import org.joda.time.*;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Test for {@link JodaTimeSingleFieldPeriodConverter}
 */
public class JodaTimeSingleFieldPeriodConverterTest {

    JodaTimeSingleFieldPeriodConverter converter = new JodaTimeSingleFieldPeriodConverter();


    @Test
    public void convert() throws Exception {
        Integer period = 5;

        Seconds seconds = (Seconds) converter.convert(period, TypeToken.of(Seconds.class));
        assertEquals(5, seconds.getSeconds());

        Minutes minutes = (Minutes) converter.convert(period, TypeToken.of(Minutes.class));
        assertEquals(5, minutes.getMinutes());

        Hours hours = (Hours) converter.convert(period, TypeToken.of(Hours.class));
        assertEquals(5, hours.getHours());

        Days days = (Days) converter.convert(period, TypeToken.of(Days.class));
        assertEquals(5, days.getDays());

        Weeks weeks = (Weeks) converter.convert(period, TypeToken.of(Weeks.class));
        assertEquals(5, weeks.getWeeks());

        Months months = (Months) converter.convert(period, TypeToken.of(Months.class));
        assertEquals(5, months.getMonths());

        Years years = (Years) converter.convert(period, TypeToken.of(Years.class));
        assertEquals(5, years.getYears());

    }

    @Test(expected = ConverterException.class)
    public void testConvertExceptionBySource() {
        converter.convert(new Date(), TypeToken.of(Hours.class));
    }

    @Test(expected = ConverterException.class)
    public void testConvertExceptionByDest() {
        converter.convert(5, TypeToken.of(Duration.class));
    }

    @Test
    public void canHandle() throws Exception {
        assertTrue(converter.canHandle("5", TypeToken.of(Hours.class)));
        assertTrue(converter.canHandle(100, TypeToken.of(Days.class)));
        assertTrue(converter.canHandle(100, TypeToken.of(Years.class)));

        assertFalse(converter.canHandle("2017-09-03", TypeToken.of(Months.class)));
    }

}