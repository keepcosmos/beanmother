package io.beanmother.java8.converter;

import io.beanmother.core.converter.AbstractGenericConverter;
import io.beanmother.core.converter.Converter;
import io.beanmother.core.converter.ConverterModule;
import io.beanmother.core.converter.std.StringToDateConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Java8 time converter module
 */
public class JavaTimeConverterModule implements ConverterModule {

    private static final StringToDateConverter stringToDateConverter = new StringToDateConverter();
    private static final ZoneId DEFAULT_TIMEZONE = ZoneId.systemDefault();

    private static final Set<Converter> converters;

    static {
        converters = new HashSet<>();
        converters.add(new DateToLocalDateTimeConverter());
        converters.add(new DateToLocalTimeConverter());
        converters.add(new DateToLocalDateConverter());
        converters.add(new StringToLocalDateTimeConverter());
        converters.add(new StringToLocalTimeConverter());
        converters.add(new StringToLocalDateConverter());
    }

    @Override
    public Set<Converter> getConverters() {
        return converters;
    }

    private static LocalDateTime convertDateToLocalDateTime(Date source) {

        return LocalDateTime.ofInstant(source.toInstant(), DEFAULT_TIMEZONE);
    }

    /**
     * Date to LocalDate converter.
     */
    public static class DateToLocalDateTimeConverter extends AbstractGenericConverter<Date, LocalDateTime> {
        @Override
        public LocalDateTime convert(Date source) {
            return convertDateToLocalDateTime(source);
        }
    }

    /**
     * Date to LocalTime converter.
     */
    public static class DateToLocalTimeConverter extends AbstractGenericConverter<Date, LocalTime> {
        @Override
        public LocalTime convert(Date source) {
            return convertDateToLocalDateTime(source).toLocalTime();
        }
    }

    /**
     * Date to LocalDate converter.
     */
    public static class DateToLocalDateConverter extends AbstractGenericConverter<Date, LocalDate> {
        @Override
        public LocalDate convert(Date source) {
            return convertDateToLocalDateTime(source).toLocalDate();
        }
    }

    /**
     * String to LocalDate converter.
     */
    public static class StringToLocalDateTimeConverter extends AbstractGenericConverter<String, LocalDateTime> {
        @Override
        public LocalDateTime convert(String source) {
            return convertDateToLocalDateTime(stringToDateConverter.convert(source));
        }
    }

    /**
     * String to LocalTime converter.
     */
    public static class StringToLocalTimeConverter extends AbstractGenericConverter<String, LocalTime> {
        @Override
        public LocalTime convert(String source) {
            return convertDateToLocalDateTime(stringToDateConverter.convert(source)).toLocalTime();
        }
    }

    /**
     * String to LocalDate converter.
     */
    public static class StringToLocalDateConverter extends AbstractGenericConverter<String, LocalDate> {
        @Override
        public LocalDate convert(String source) {
            return convertDateToLocalDateTime(stringToDateConverter.convert(source)).toLocalDate();
        }
    }
}
