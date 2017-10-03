package io.beanmother.joda.converter;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.ConverterException;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Test for {@link StringToJodaTimeBaseLocalConverter}
 */
public class StringToJodaTimeBaseLocalConverterTest {
    StringToJodaTimeBaseLocalConverter converter = new StringToJodaTimeBaseLocalConverter();

    @Test
    public void convert() throws Exception {
        String dateString = "1985-09-03 13:30";

        LocalTime localTime = (LocalTime) converter.convert(dateString, TypeToken.of(LocalTime.class));
        assertEquals(13, localTime.getHourOfDay());
        assertEquals(30, localTime.getMinuteOfHour());

        LocalDate localDate = (LocalDate) converter.convert(dateString, TypeToken.of(LocalDate.class));
        assertEquals(1985, localDate.getYear());
        assertEquals(9, localDate.getMonthOfYear());
        assertEquals(3, localDate.getDayOfMonth());

        LocalDateTime localDateTime = (LocalDateTime) converter.convert(dateString, TypeToken.of(LocalDateTime.class));
        assertEquals(13, localDateTime.getHourOfDay());
        assertEquals(30, localDateTime.getMinuteOfHour());
        assertEquals(1985, localDateTime.getYear());
        assertEquals(9, localDateTime.getMonthOfYear());
        assertEquals(3, localDateTime.getDayOfMonth());
    }

    @Test(expected = ConverterException.class)
    public void testConvertExceptionBySource() {
        converter.convert(new Date(), TypeToken.of(LocalTime.class));
    }

    @Test(expected = ConverterException.class)
    public void testConvertExceptionByDest() {
        converter.convert("2017-09-03", TypeToken.of(Duration.class));
    }

    @Test
    public void canHandle() throws Exception {
        assertTrue(converter.canHandle("2017-09-03", TypeToken.of(LocalTime.class)));
        assertTrue(converter.canHandle("2017-09-03", TypeToken.of(LocalDate.class)));
        assertTrue(converter.canHandle("2017-09-03", TypeToken.of(LocalDateTime.class)));

        assertFalse(converter.canHandle("2017-09-03", TypeToken.of(Duration.class)));
        assertFalse(converter.canHandle(new Date(), TypeToken.of(LocalDate.class)));
    }
}