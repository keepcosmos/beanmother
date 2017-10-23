package io.beanmother.guava.converter;

import com.google.common.base.Optional;
import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.AbstractConverter;
import io.beanmother.core.converter.Converter;
import io.beanmother.core.converter.ConverterModule;
import io.beanmother.core.converter.std.NumberToNumberConverter;
import io.beanmother.core.converter.std.StringToNumberConverter;

import java.util.HashSet;
import java.util.Set;

/**
 * Guava Optional converter module
 */
public class GuavaOptionalConverterModule implements ConverterModule {

    private static final Converter numberConverter = new NumberToNumberConverter();
    private static final Converter stringToNumberConverter = new StringToNumberConverter();

    private static final Set<Converter> converters;

    static {
        converters = new HashSet<>();
        converters.add(new NumberToOptionalOfIntegerConverter());
        converters.add(new NumberToOptionalOfLongConverter());
        converters.add(new NumberToOptionalOfDoubleConverter());
        converters.add(new StringToOptionalOfIntegerConverter());
        converters.add(new StringToOptionalOfLongConverter());
        converters.add(new StringToOptionalOfDoubleConverter());
    }

    @Override
    public Set<Converter> getConverters() {
        return converters;
    }

    /**
     * Converter used to convert a Number to a Optional of Integer
     */
    public static class NumberToOptionalOfIntegerConverter extends AbstractConverter {

        @Override
        public Object convert(Object source, TypeToken<?> targetTypeToken) {
            Object converted = numberConverter.convert(source, TypeToken.of(Integer.class));

            if (converted == null) {
                return Optional.absent();
            } else {
                return Optional.of((Integer) converted);
            }
        }

        @Override
        public boolean canHandle(Object source, TypeToken<?> targetTypeToken) {
            return numberConverter.canHandle(source, TypeToken.of(Integer.class))
                    && targetTypeToken.equals(new TypeToken<Optional<Integer>>(){});
        }
    }

    /**
     * Converter used to convert a Number to a Optional of Long
     */
    public static class NumberToOptionalOfLongConverter extends AbstractConverter {

        @Override
        public Object convert(Object source, TypeToken<?> targetTypeToken) {
            Object converted = numberConverter.convert(source, TypeToken.of(Long.class));

            if (converted == null) {
                return Optional.absent();
            } else {
                return Optional.of((Long) converted);
            }
        }

        @Override
        public boolean canHandle(Object source, TypeToken<?> targetTypeToken) {
            return numberConverter.canHandle(source, TypeToken.of(Long.class))
                    && targetTypeToken.equals(new TypeToken<Optional<Long>>(){});
        }
    }

    /**
     * Converter used to convert a Number to a Optional of Double
     */
    public static class NumberToOptionalOfDoubleConverter extends AbstractConverter {

        @Override
        public Object convert(Object source, TypeToken<?> targetTypeToken) {
            Object converted = numberConverter.convert(source, TypeToken.of(Double.class));

            if (converted == null) {
                return Optional.absent();
            } else {
                return Optional.of((Double) converted);
            }
        }

        @Override
        public boolean canHandle(Object source, TypeToken<?> targetTypeToken) {
            return numberConverter.canHandle(source, TypeToken.of(Double.class))
                    && targetTypeToken.equals(new TypeToken<Optional<Double>>(){});
        }
    }

    /**
     * Converter used to convert a String to a Optional of Integer
     */
    public static class StringToOptionalOfIntegerConverter extends AbstractConverter {

        @Override
        public Object convert(Object source, TypeToken<?> targetTypeToken) {
            Object converted = stringToNumberConverter.convert(source, TypeToken.of(Integer.class));

            if (converted == null) {
                return Optional.absent();
            } else {
                return Optional.of((Integer) converted);
            }
        }

        @Override
        public boolean canHandle(Object source, TypeToken<?> targetTypeToken) {
            return stringToNumberConverter.canHandle(source, TypeToken.of(Integer.class))
                    && targetTypeToken.equals(new TypeToken<Optional<Integer>>(){});
        }
    }

    /**
     * Converter used to convert a String to a Optional of Long
     */
    public static class StringToOptionalOfLongConverter extends AbstractConverter {

        @Override
        public Object convert(Object source, TypeToken<?> targetTypeToken) {
            Object converted = stringToNumberConverter.convert(source, TypeToken.of(Long.class));

            if (converted == null) {
                return Optional.absent();
            } else {
                return Optional.of((Long) converted);
            }
        }

        @Override
        public boolean canHandle(Object source, TypeToken<?> targetTypeToken) {
            return stringToNumberConverter.canHandle(source, TypeToken.of(Long.class))
                    && targetTypeToken.equals(new TypeToken<Optional<Long>>(){});
        }
    }

    /**
     * Converter used to convert a String to a Optional of Double
     */
    public static class StringToOptionalOfDoubleConverter extends AbstractConverter {

        @Override
        public Object convert(Object source, TypeToken<?> targetTypeToken) {
            Object converted = stringToNumberConverter.convert(source, TypeToken.of(Double.class));

            if (converted == null) {
                return Optional.absent();
            } else {
                return Optional.of((Double) converted);
            }
        }

        @Override
        public boolean canHandle(Object source, TypeToken<?> targetTypeToken) {
            return stringToNumberConverter.canHandle(source, TypeToken.of(Double.class))
                    && targetTypeToken.equals(new TypeToken<Optional<Double>>(){});
        }
    }
}
