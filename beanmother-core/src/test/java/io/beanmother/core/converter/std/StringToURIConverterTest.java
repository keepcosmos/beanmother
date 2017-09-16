package io.beanmother.core.converter.std;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.ConverterException;
import org.junit.Test;

import java.net.URI;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Test for {@link StringToURIConverter}
 */
public class StringToURIConverterTest {

    StringToURIConverter converter = new StringToURIConverter();

    @Test
    public void testCanHandle() {
        assertTrue(converter.canHandle("http://www.google.com", TypeToken.of(URI.class)));

        assertFalse(converter.canHandle("http://www.google.com", TypeToken.of(URL.class)));
    }

    @Test
    public void testConvert() {
        URI uri = converter.convert("http://www.google.com");
        assertEquals("http", uri.getScheme());
        assertEquals("www.google.com", uri.getHost());
    }

    @Test(expected = ConverterException.class)
    public void testRaiseException() {
        URI uri = converter.convert("&&##3");
    }
}