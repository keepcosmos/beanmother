package io.beanmother.java8.converter;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.Converter;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link JavaTimeConverterModule}
 */
public class JavaTimeConverterModuleTest {

    Converter converter;

    @Test
    public void testDateToLocalDateTimeConverter() throws ParseException {
        converter = new JavaTimeConverterModule.DateToLocalDateTimeConverter();

        String dateString = "06/27/2017 12:30";
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date date = df.parse(dateString);


        assertTrue(converter.canHandle(date, TypeToken.of(LocalDateTime.class)));
        assertFalse(converter.canHandle(date, TypeToken.of(LocalDate.class)));

        LocalDateTime dateTime = (LocalDateTime) converter.convert(date, TypeToken.of(LocalDateTime.class));
        assertEquals(6, dateTime.getMonthValue());
        assertEquals(27, dateTime.getDayOfMonth());
        assertEquals(2017, dateTime.getYear());
        assertEquals(12, dateTime.getHour());
        assertEquals(30, dateTime.getMinute());
    }

    @Test
    public void testDateToLocalTimeConverter() throws ParseException {
        converter = new JavaTimeConverterModule.DateToLocalTimeConverter();

        String dateString = "06/27/2017 12:30";
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date date = df.parse(dateString);

        assertTrue(converter.canHandle(date, TypeToken.of(LocalTime.class)));
        assertFalse(converter.canHandle(date, TypeToken.of(LocalDate.class)));

        LocalTime dateTime = (LocalTime) converter.convert(date, TypeToken.of(LocalTime.class));
        assertEquals(12, dateTime.getHour());
        assertEquals(30, dateTime.getMinute());
    }

    @Test
    public void testDateToLocalDateConverter() throws ParseException {
        converter = new JavaTimeConverterModule.DateToLocalDateConverter();

        String dateString = "06/27/2017 12:30";
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date date = df.parse(dateString);

        assertTrue(converter.canHandle(date, TypeToken.of(LocalDate.class)));
        assertFalse(converter.canHandle(date, TypeToken.of(LocalTime.class)));


        LocalDate dateTime = (LocalDate) converter.convert(date, TypeToken.of(LocalDate.class));
        assertEquals(6, dateTime.getMonthValue());
        assertEquals(27, dateTime.getDayOfMonth());
        assertEquals(2017, dateTime.getYear());
    }

    @Test
    public void testStringToLocalDateTimeConverter() {
        converter = new JavaTimeConverterModule.StringToLocalDateTimeConverter();

        String dateString = "06/27/2017 12:30";

        assertTrue(converter.canHandle(dateString, TypeToken.of(LocalDateTime.class)));
        assertFalse(converter.canHandle(dateString, TypeToken.of(LocalDate.class)));

        LocalDateTime dateTime = (LocalDateTime) converter.convert(dateString, TypeToken.of(LocalDateTime.class));
        assertEquals(6, dateTime.getMonthValue());
        assertEquals(27, dateTime.getDayOfMonth());
        assertEquals(2017, dateTime.getYear());
        assertEquals(12, dateTime.getHour());
        assertEquals(30, dateTime.getMinute());
    }

    @Test
    public void testStringToLocalTimeConverter() {
        converter = new JavaTimeConverterModule.StringToLocalTimeConverter();

        String dateString = "12:30";

        assertTrue(converter.canHandle(dateString, TypeToken.of(LocalTime.class)));
        assertFalse(converter.canHandle(dateString, TypeToken.of(LocalDate.class)));

        LocalTime dateTime = (LocalTime) converter.convert(dateString, TypeToken.of(LocalTime.class));
        assertEquals(12, dateTime.getHour());
        assertEquals(30, dateTime.getMinute());
    }

    @Test
    public void testStringToLocalDateConverter() {
        converter = new JavaTimeConverterModule.StringToLocalDateConverter();

        String dateString = "06/27/2017";

        assertTrue(converter.canHandle(dateString, TypeToken.of(LocalDate.class)));
        assertFalse(converter.canHandle(dateString, TypeToken.of(LocalTime.class)));

        LocalDate dateTime = (LocalDate) converter.convert(dateString, TypeToken.of(LocalDate.class));
        assertEquals(6, dateTime.getMonthValue());
        assertEquals(27, dateTime.getDayOfMonth());
        assertEquals(2017, dateTime.getYear());
    }
}