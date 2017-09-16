package io.beanmother.core.converter.std;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.ConverterException;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link StringToBooleanConverter}
 */
public class StringToBooleanConverterTest {
    StringToBooleanConverter converter = new StringToBooleanConverter();

    @Test
    public void testCanHandle() {
        assertTrue(converter.canHandle("true", TypeToken.of(Boolean.class)));
        assertFalse(converter.canHandle("test", TypeToken.of(Integer.class)));

    }

    @Test
    public void testConvert() {
        assertTrue(converter.convert("yes"));
        assertTrue(converter.convert("t"));
        assertTrue(converter.convert("true"));

        assertFalse(converter.convert("false"));
        assertFalse(converter.convert("f"));
        assertFalse(converter.convert("no"));
    }

    @Test(expected = ConverterException.class)
    public void testRaiseException() {
        converter.convert("abc");
    }

}