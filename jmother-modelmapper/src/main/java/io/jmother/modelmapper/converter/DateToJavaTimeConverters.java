package io.jmother.modelmapper.converter;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * {@link AbstractConverter} implementation for {@link org.modelmapper.ModelMapper} for converting {@link Date} to {@link java.time}
 */
public class DateToJavaTimeConverters {

    /**
     * Instance of {@link DateToLocalDateConverter}
     */
    public static Converter<Date, LocalDate> DATE_TO_LOCAL_DATE = new DateToLocalDateConverter();

    /**
     * Instance of {@link DateToLocalTimeConverter}
     */
    public static Converter<Date, LocalTime> DATE_TO_LOCAL_TIME = new DateToLocalTimeConverter();

    /**
     * Instance of {@link DateToLocalDateTimeConverter}
     */
    public static Converter<Date, LocalDateTime> DATE_TO_LOCAL_DATETIME = new DateToLocalDateTimeConverter();

    /**
     * {@link Date} to {@link LocalDate}
     */
    public static class DateToLocalDateConverter extends AbstractConverter<Date, LocalDate> {
        @Override
        public LocalDate convert(Date source) {
            return source.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
    }

    /**
     * {@link Date} to {@link LocalTime}
     */
    public static class DateToLocalTimeConverter extends AbstractConverter<Date, LocalTime> {
        @Override
        protected LocalTime convert(Date source) {
            return source.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        }
    }

    /**
     * {@link Date} to {@link LocalDateTime}
     */
    public static class DateToLocalDateTimeConverter extends AbstractConverter<Date, LocalDateTime> {
        @Override
        protected LocalDateTime convert(Date source) {
            return source.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
    }
}
