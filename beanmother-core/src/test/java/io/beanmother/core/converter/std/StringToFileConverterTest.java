package io.beanmother.core.converter.std;

import com.google.common.reflect.TypeToken;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Test for {@link StringToFileConverter}
 */
public class StringToFileConverterTest {

    StringToFileConverter converter = new StringToFileConverter();

    @Test
    public void testCanHandle() {
        assertTrue(converter.canHandle("path", TypeToken.of(File.class)));
        assertFalse(converter.canHandle("path", TypeToken.of(Object.class)));
    }

    @Test
    public void testConvert() {
        File file = converter.convert("path");
        assertEquals("path", file.getName());
    }

}