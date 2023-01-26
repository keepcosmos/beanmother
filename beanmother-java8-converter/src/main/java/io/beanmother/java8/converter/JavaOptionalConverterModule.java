package io.beanmother.java8.converter;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.AbstractConverter;
import io.beanmother.core.converter.Converter;
import io.beanmother.core.converter.ConverterModule;
import io.beanmother.core.converter.std.NumberToNumberConverter;
import io.beanmother.core.converter.std.StringToNumberConverter;

import java.util.*;


/**
 * Java8 Optional converter module
 */
public class JavaOptionalConverterModule implements ConverterModule {

    private static final Converter numberConverter = new NumberToNumberConverter();
    private static final Converter stringToNumberConverter = new StringToNumberConverter();

    private static final Set<Converter> converters;

    static {
        converters = new HashSet<>();
        converters.add(new NumberToOptionalIntConverter());
        converters.add(new NumberToOptionalDoubleConverter());
        converters.add(new NumberToOptionalLongConverter());
        converters.add(new StringToOptionalIntConverter());
        converters.add(new StringToOptionalDoubleConverter());
        converters.add(new StringToOptionalLongConverter());
    }

    @Override
    public Set<Converter> getConverters() {
        return converters;
    }

    /**
     * Converter used to convert a Number to a OptionalInt
     */
    public static class NumberToOptionalIntConverter extends AbstractConverter {

        @Override
        public Object convert(Object source, TypeToken<?> targetTypeToken) {
            Object converted = numberConverter.convert(source, TypeToken.of(Integer.class));

            if (converted == null) {
                return OptionalInt.empty();
            } else {
                return OptionalInt.of((Integer) converted);
            }
        }

        @Override
        public boolean canHandle(Object source, TypeToken<?> targetTypeToken) {
            return numberConverter.canHandle(source, TypeToken.of(Integer.class))
                    && targetTypeToken.equals(TypeToken.of(OptionalInt.class));
        }
    }

    /**
     * Converter used to convert a Number to a OptionalDouble
     */
    public static class NumberToOptionalDoubleConverter extends AbstractConverter {

        @Override
        public Object convert(Object source, TypeToken<?> targetTypeToken) {
            Object converted = numberConverter.convert(source, TypeToken.of(Double.class));

            if (converted == null) {
                return OptionalDouble.empty();
            } else {
                return OptionalDouble.of((Double) converted);
            }
        }

        @Override
        public boolean canHandle(Object source, TypeToken<?> targetTypeToken) {
            return numberConverter.canHandle(source, TypeToken.of(Double.class))
                    && targetTypeToken.equals(TypeToken.of(OptionalDouble.class));
        }
    }

    /**
     * Converter used to convert a Number to a OptionalLong
     */
    public static class NumberToOptionalLongConverter extends AbstractConverter {

        @Override
        public Object convert(Object source, TypeToken<?> targetTypeToken) {
            Object converted = numberConverter.convert(source, TypeToken.of(Long.class));

            if (converted == null) {
                return OptionalLong.empty();
            } else {
                return OptionalLong.of((Long) converted);
            }
        }

        @Override
        public boolean canHandle(Object source, TypeToken<?> targetTypeToken) {
            return numberConverter.canHandle(source, TypeToken.of(Long.class))
                    && targetTypeToken.equals(TypeToken.of(OptionalLong.class));
        }
    }

    /**
     * Converter used to convert a String to a OptionalInt
     */
    public static class StringToOptionalIntConverter extends AbstractConverter {

        @Override
        public Object convert(Object source, TypeToken<?> targetTypeToken) {
            Object converted = stringToNumberConverter.convert(source, TypeToken.of(Integer.class));

            if (converted == null) {
                return OptionalInt.empty();
            } else {
                return OptionalInt.of((Integer) converted);
            }
        }

        @Override
        public boolean canHandle(Object source, TypeToken<?> targetTypeToken) {
            return stringToNumberConverter.canHandle(source, TypeToken.of(Integer.class))
                    && targetTypeToken.equals(TypeToken.of(OptionalInt.class));
        }
    }

    /**
     * Converter used to convert a String to a OptionalDouble
     */
    public static class StringToOptionalDoubleConverter extends AbstractConverter {

        @Override
        public Object convert(Object source, TypeToken<?> targetTypeToken) {
            Object converted = stringToNumberConverter.convert(source, TypeToken.of(Double.class));

            if (converted == null) {
                return OptionalDouble.empty();
            } else {
                return OptionalDouble.of((Double) converted);
            }
        }

        @Override
        public boolean canHandle(Object source, TypeToken<?> targetTypeToken) {
            return stringToNumberConverter.canHandle(source, TypeToken.of(Double.class))
                    && targetTypeToken.equals(TypeToken.of(OptionalDouble.class));
        }
    }

    /**
     * Converter used to convert a String to a OptionalLong
     */
    public static class StringToOptionalLongConverter extends AbstractConverter {

        @Override
        public Object convert(Object source, TypeToken<?> targetTypeToken) {
            Object converted = stringToNumberConverter.convert(source, TypeToken.of(Long.class));

            if (converted == null) {
                return OptionalLong.empty();
            } else {
                return OptionalLong.of((Long) converted);
            }
        }

        @Override
        public boolean canHandle(Object source, TypeToken<?> targetTypeToken) {
            return stringToNumberConverter.canHandle(source, TypeToken.of(Long.class))
                    && targetTypeToken.equals(TypeToken.of(OptionalLong.class));
        }
    }
}
