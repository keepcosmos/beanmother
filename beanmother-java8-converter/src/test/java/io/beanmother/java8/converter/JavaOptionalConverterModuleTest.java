package io.beanmother.java8.converter;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.Converter;
import org.junit.Test;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

import static org.junit.Assert.*;

/**
 * Test for {@link JavaOptionalConverterModule}
 */
public class JavaOptionalConverterModuleTest {

    Converter converter;

    @Test
    public void testNumberToOptionalIntConverter(){
        converter = new JavaOptionalConverterModule.NumberToOptionalIntConverter();

        assertTrue(converter.canHandle(1l, TypeToken.of(OptionalInt.class)));
        assertFalse(converter.canHandle(1l, TypeToken.of(OptionalLong.class)));

        Object result = converter.convert(1l, TypeToken.of(OptionalInt.class));
        assertTrue(result instanceof OptionalInt);
        assertEquals(1, ((OptionalInt) result).getAsInt());
    }

    @Test
    public void testNumberToOptionalDoubleConverter() {
        converter = new JavaOptionalConverterModule.NumberToOptionalDoubleConverter();

        assertTrue(converter.canHandle(1l, TypeToken.of(OptionalDouble.class)));
        assertFalse(converter.canHandle(1l, TypeToken.of(OptionalLong.class)));

        Object result = converter.convert(1l, TypeToken.of(OptionalDouble.class));
        assertTrue(result instanceof OptionalDouble);
        assertEquals(1, ((OptionalDouble) result).getAsDouble(), 0.01);
    }

    @Test
    public void testNumberToOptionalLongConverter() {
        converter = new JavaOptionalConverterModule.NumberToOptionalLongConverter();

        assertTrue(converter.canHandle(1f, TypeToken.of(OptionalLong.class)));
        assertFalse(converter.canHandle(1f, TypeToken.of(OptionalInt.class)));

        Object result = converter.convert(1f, TypeToken.of(OptionalLong.class));
        assertTrue(result instanceof OptionalLong);
        assertEquals(1l, ((OptionalLong) result).getAsLong());
    }

    @Test
    public void testStringToOptionalIntConverter() {
        converter = new JavaOptionalConverterModule.StringToOptionalIntConverter();

        assertTrue(converter.canHandle("1", TypeToken.of(OptionalInt.class)));
        assertFalse(converter.canHandle("1", TypeToken.of(OptionalLong.class)));

        Object result = converter.convert("1", TypeToken.of(OptionalInt.class));
        assertTrue(result instanceof OptionalInt);
        assertEquals(1, ((OptionalInt) result).getAsInt());
    }

    @Test
    public void testStringToOptionalDoubleConverter() {
        converter = new JavaOptionalConverterModule.StringToOptionalDoubleConverter();

        assertTrue(converter.canHandle("1", TypeToken.of(OptionalDouble.class)));
        assertFalse(converter.canHandle("1", TypeToken.of(OptionalLong.class)));

        Object result = converter.convert("1", TypeToken.of(OptionalDouble.class));
        assertTrue(result instanceof OptionalDouble);
        assertEquals(1, ((OptionalDouble) result).getAsDouble(), 0.01);
    }

    @Test
    public void testStringToOptionalLongConverter() {
        converter = new JavaOptionalConverterModule.StringToOptionalLongConverter();

        assertTrue(converter.canHandle("1", TypeToken.of(OptionalLong.class)));
        assertFalse(converter.canHandle("1", TypeToken.of(OptionalInt.class)));

        Object result = converter.convert("1", TypeToken.of(OptionalLong.class));
        assertTrue(result instanceof OptionalLong);
        assertEquals(1, ((OptionalLong) result).getAsLong());
    }
}