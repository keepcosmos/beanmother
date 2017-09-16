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

public class JavaTimeConverterModule implements ConverterModule {

    private final static StringToDateConverter stringToDateConverter = new StringToDateConverter();
    private final static ZoneId DEFAULT_TIMEZONE = ZoneId.systemDefault();

    private final static Set<Converter> converters;

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

    public static class DateToLocalDateTimeConverter extends AbstractGenericConverter<Date, LocalDateTime> {
        @Override
        public LocalDateTime convert(Date source) {
            return convertDateToLocalDateTime(source);
        }
    }

    public static class DateToLocalTimeConverter extends AbstractGenericConverter<Date, LocalTime> {
        @Override
        public LocalTime convert(Date source) {
            return convertDateToLocalDateTime(source).toLocalTime();
        }
    }

    public static class DateToLocalDateConverter extends AbstractGenericConverter<Date, LocalDate> {
        @Override
        public LocalDate convert(Date source) {
            return convertDateToLocalDateTime(source).toLocalDate();
        }
    }

    public static class StringToLocalDateTimeConverter extends AbstractGenericConverter<String, LocalDateTime> {
        @Override
        public LocalDateTime convert(String source) {
            return convertDateToLocalDateTime(stringToDateConverter.convert(source));
        }
    }

    public static class StringToLocalTimeConverter extends AbstractGenericConverter<String, LocalTime> {
        @Override
        public LocalTime convert(String source) {
            return convertDateToLocalDateTime(stringToDateConverter.convert(source)).toLocalTime();
        }
    }

    public static class StringToLocalDateConverter extends AbstractGenericConverter<String, LocalDate> {
        @Override
        public LocalDate convert(String source) {
            return convertDateToLocalDateTime(stringToDateConverter.convert(source)).toLocalDate();
        }
    }
}
