package io.beanmother.core.converter.std;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.ConverterException;
import org.junit.Test;

import java.net.URI;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Test for {@link StringToURLConverter}
 */
public class StringToURLConverterTest {

    StringToURLConverter converter = new StringToURLConverter();

    @Test
    public void testCanHandle() {
        assertTrue(converter.canHandle("http://www.google.com", TypeToken.of(URL.class)));

        assertFalse(converter.canHandle("http://www.google.com", TypeToken.of(URI.class)));
    }

    @Test
    public void testConvert() {
        URL url = converter.convert("http://www.google.com");
        assertEquals("http", url.getProtocol());
        assertEquals("www.google.com", url.getHost());
    }

    @Test(expected = ConverterException.class)
    public void testRaiseException() {
        converter.convert("&&##3");
    }
}