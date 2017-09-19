package io.beanmother.core.converter.std;

import com.google.common.reflect.TypeToken;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link ObjectToStringConverter}
 */
public class ObjectToStringConverterTest {

    ObjectToStringConverter converter = new ObjectToStringConverter();

    @Test
    public void testCanHandle() {
        assertTrue(converter.canHandle(new Date(), TypeToken.of(String.class)));
        assertTrue(converter.canHandle("test", TypeToken.of(String.class)));
        assertFalse(converter.canHandle(new Date(), TypeToken.of(Date.class)));
    }

    @Test
    public void testConvert() throws ParseException {
        assertEquals("1", converter.convert(1l, TypeToken.of(String.class)));
        assertEquals("test", converter.convert("test", TypeToken.of(String.class)));
        assertEquals("true", converter.convert(true, TypeToken.of(String.class)));
    }
}