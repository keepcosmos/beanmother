package io.beanmother.core.converter;

import com.google.common.reflect.TypeToken;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link AbstractConverter}
 */
public class AbstractGenericConverterTest {

    public static class TestGenericConverter extends AbstractGenericConverter<Integer, String> {
        @Override
        public String convert(Integer source) {
            return null;
        }
    }

    @Test
    public void testCanHandle() throws Exception {
        TestGenericConverter converter = new TestGenericConverter();

        assertTrue(converter.canHandle(1, TypeToken.of(String.class)));
        assertFalse(converter.canHandle(1l, TypeToken.of(String.class)));
        assertFalse(converter.canHandle(1, TypeToken.of(Long.class)));
    }

    @Test
    public void getSourceTypeToken() throws Exception {
        TestGenericConverter converter = new TestGenericConverter();
        assertTrue(converter.getSourceTypeToken().equals(TypeToken.of(Integer.class)));
    }

    @Test
    public void getTargetTypeToken() throws Exception {
        TestGenericConverter converter = new TestGenericConverter();
        assertTrue(converter.getTargetTypeToken().equals(TypeToken.of(String.class)));
    }

}