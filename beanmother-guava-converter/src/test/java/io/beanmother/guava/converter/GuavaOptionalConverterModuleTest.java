package io.beanmother.guava.converter;

import com.google.common.base.Optional;
import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.Converter;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for {@link GuavaOptionalConverterModule}
 */
public class GuavaOptionalConverterModuleTest {

    Converter converter;

    @Test
    public void testNumberToOptionalOfIntegerConverter() {
        converter = new GuavaOptionalConverterModule.NumberToOptionalOfIntegerConverter();

        assertTrue(converter.canHandle(1, new TypeToken<Optional<Integer>>() {}));

        Object result = converter.convert(1, new TypeToken<Optional<Integer>>() {});
        assertTrue(result instanceof Optional);
        assertEquals(Integer.valueOf(1), ((Optional<Integer>) result).get());
    }
    @Test
    public void testNumberToOptionalOfLongConverter() {
        converter = new GuavaOptionalConverterModule.NumberToOptionalOfLongConverter();

        assertTrue(converter.canHandle(1L, new TypeToken<Optional<Long>>() {}));

        Object result = converter.convert(1L, new TypeToken<Optional<Long>>() {});
        assertTrue(result instanceof Optional);
        assertEquals(Long.valueOf(1), ((Optional<Long>) result).get());
    }

    @Test
    public void testNumberToOptionalOfDoubleConverter() {
        converter = new GuavaOptionalConverterModule.NumberToOptionalOfDoubleConverter();

        assertTrue(converter.canHandle(1.0, new TypeToken<Optional<Double>>() {}));

        Object result = converter.convert(1.0, new TypeToken<Optional<Double>>() {});
        assertTrue(result instanceof Optional);
        assertEquals(Double.valueOf(1), ((Optional<Double>) result).get());
    }

    @Test
    public void testStringToOptionalOfIntegerConverter() {
        converter = new GuavaOptionalConverterModule.StringToOptionalOfIntegerConverter();

        assertTrue(converter.canHandle("1", new TypeToken<Optional<Integer>>() {}));

        Object result = converter.convert("1", new TypeToken<Optional<Integer>>() {});
        assertTrue(result instanceof Optional);
        assertEquals(Integer.valueOf(1), ((Optional<Integer>) result).get());
    }

    @Test
    public void testStringToOptionalOfLongConverter() {
        converter = new GuavaOptionalConverterModule.StringToOptionalOfLongConverter();

        assertTrue(converter.canHandle("1", new TypeToken<Optional<Long>>() {}));

        Object result = converter.convert("1", new TypeToken<Optional<Long>>() {});
        assertTrue(result instanceof Optional);
        assertEquals(Long.valueOf(1), ((Optional<Long>) result).get());
    }

    @Test
    public void testStringToOptionalOfDoubleConverter() {
        converter = new GuavaOptionalConverterModule.StringToOptionalOfDoubleConverter();

        assertTrue(converter.canHandle("1", new TypeToken<Optional<Double>>() {}));

        Object result = converter.convert("1", new TypeToken<Optional<Double>>() {});
        assertTrue(result instanceof Optional);
        assertEquals(Double.valueOf(1), ((Optional<Double>) result).get());
    }
}