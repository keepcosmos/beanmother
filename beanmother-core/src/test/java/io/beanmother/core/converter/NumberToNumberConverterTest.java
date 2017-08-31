package io.beanmother.core.converter;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.std.NumberToNumberConverter;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link NumberToNumberConverter}
 */
public class NumberToNumberConverterTest {

    NumberToNumberConverter converter = new NumberToNumberConverter();

    @Test
    public void testMatches() {
        assertTrue(converter.canHandle(10, TypeToken.of(Float.class)));
        assertTrue(converter.canHandle(5f, TypeToken.of(Integer.class)));
        assertTrue(converter.canHandle(1, TypeToken.of(BigDecimal.class)));
        assertTrue(converter.canHandle(10f, TypeToken.of(AtomicInteger.class)));
        assertTrue(converter.canHandle(10, TypeToken.of(Long.class)));
        assertTrue(converter.canHandle(11, TypeToken.of(Short.class)));
        assertTrue(converter.canHandle(12, TypeToken.of(Byte.class)));
        assertTrue(converter.canHandle(13, TypeToken.of(BigInteger.class)));
    }

    @Test
    public void testConvert() {
        Integer integer = 10;

        assertEquals(integer.byteValue(), converter.convert(integer, TypeToken.of(Byte.class)));
        assertEquals(integer.floatValue(), converter.convert(integer, TypeToken.of(Float.class)));
        assertEquals(integer.doubleValue(), converter.convert(integer, TypeToken.of(Double.class)));
        assertEquals(integer.intValue(), converter.convert(integer, TypeToken.of(Integer.class)));
        assertEquals(integer.shortValue(), converter.convert(integer, TypeToken.of(Short.class)));

        assertEquals(converter.convert(integer, TypeToken.of(AtomicInteger.class)).getClass(), AtomicInteger.class);
        assertEquals(converter.convert(integer, TypeToken.of(AtomicLong.class)).getClass(), AtomicLong.class);

    }
}