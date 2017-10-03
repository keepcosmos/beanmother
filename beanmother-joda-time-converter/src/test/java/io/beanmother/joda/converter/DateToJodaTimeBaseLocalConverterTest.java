package io.beanmother.joda.converter;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.ConverterException;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link DateToJodaTimeBaseLocalConverter}
 */
public class DateToJodaTimeBaseLocalConverterTest {
    DateToJodaTimeBaseLocalConverter converter = new DateToJodaTimeBaseLocalConverter();

    @Test
    public void convert() throws Exception {
        String dateString = "06/27/2017 12:30";
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date date = df.parse(dateString);

        LocalTime localTime = (LocalTime) converter.convert(date, TypeToken.of(LocalTime.class));
        assertEquals(12, localTime.getHourOfDay());
        assertEquals(30, localTime.getMinuteOfHour());

        LocalDate localDate = (LocalDate) converter.convert(date, TypeToken.of(LocalDate.class));
        assertEquals(2017, localDate.getYear());
        assertEquals(6, localDate.getMonthOfYear());
        assertEquals(27, localDate.getDayOfMonth());

        LocalDateTime localDateTime = (LocalDateTime) converter.convert(date, TypeToken.of(LocalDateTime.class));
        assertEquals(12, localDateTime.getHourOfDay());
        assertEquals(30, localDateTime.getMinuteOfHour());
        assertEquals(2017, localDateTime.getYear());
        assertEquals(6, localDateTime.getMonthOfYear());
        assertEquals(27, localDateTime.getDayOfMonth());
    }

    @Test(expected = ConverterException.class)
    public void testConvertExceptionBySource() {
        converter.convert("2017-01-01", TypeToken.of(LocalTime.class));
    }

    @Test(expected = ConverterException.class)
    public void testConvertExceptionByDest() {
        converter.convert(new Date(), TypeToken.of(Duration.class));
    }

    @Test
    public void canHandle() throws Exception {
        assertTrue(converter.canHandle(new Date(), TypeToken.of(LocalTime.class)));
        assertTrue(converter.canHandle(new Date(), TypeToken.of(LocalDate.class)));
        assertTrue(converter.canHandle(new Date(), TypeToken.of(LocalDateTime.class)));

        assertFalse(converter.canHandle(new Date(), TypeToken.of(Duration.class)));
        assertFalse(converter.canHandle("2017-09-03", TypeToken.of(LocalDate.class)));
    }

}