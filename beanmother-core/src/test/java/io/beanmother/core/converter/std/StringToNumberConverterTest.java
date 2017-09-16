package io.beanmother.core.converter.std;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.ConverterException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for {@link StringToNumberConverter}
 */
public class StringToNumberConverterTest {

    StringToNumberConverter converter = new StringToNumberConverter();

    @Test
    public void testCanHandle() {
        assertTrue(converter.canHandle("1", TypeToken.of(Integer.class)));
        assertTrue(converter.canHandle("1", TypeToken.of(Float.class)));
        assertTrue(converter.canHandle("1", TypeToken.of(Short.class)));
        assertTrue(converter.canHandle("1", TypeToken.of(Double.class)));
        assertTrue(converter.canHandle("1", TypeToken.of(Long.class)));

        assertTrue(converter.canHandle("1", TypeToken.of(Integer.class)));
        assertTrue(converter.canHandle("1", TypeToken.of(int.class)));
        assertTrue(converter.canHandle("1", TypeToken.of(short.class)));
        assertTrue(converter.canHandle("1", TypeToken.of(double.class)));
        assertTrue(converter.canHandle("1", TypeToken.of(long.class)));

        assertFalse(converter.canHandle("abc", TypeToken.of(Integer.class)));
        assertFalse(converter.canHandle("1", TypeToken.of(String.class)));
    }

    @Test
    public void testConvert() {
        assertEquals(1, converter.convert("1", TypeToken.of(Integer.class)));
        assertEquals(1.1d, converter.convert("1.1", TypeToken.of(Double.class)));
    }

    @Test(expected = ConverterException.class)
    public void testRaiseError() {
        converter.convert("abc", TypeToken.of(Integer.class));
    }
}