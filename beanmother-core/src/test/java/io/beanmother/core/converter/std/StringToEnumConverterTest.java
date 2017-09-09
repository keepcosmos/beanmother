package io.beanmother.core.converter.std;

import com.google.common.reflect.TypeToken;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link StringToEnumConverter}
 */
public class StringToEnumConverterTest {
    StringToEnumConverter converter = new StringToEnumConverter();

    @Test
    public void testCanHandle() {
        assertTrue(converter.canHandle("CONVERTER", TypeToken.of(TestEnum.class)));
    }

    @Test
    public void testConvert() {
        assertEquals(TestEnum.CONVERTER, converter.convert("CONVERTER", TypeToken.of(TestEnum.class)));
        assertEquals(TestEnum.CONVERTER, converter.convert("converter", TypeToken.of(TestEnum.class)));

        assertEquals(TestEnum.SCRIPT_PROCESSOR, converter.convert("SCRIPT_PROCESSOR", TypeToken.of(TestEnum.class)));
        assertEquals(TestEnum.SCRIPT_PROCESSOR, converter.convert("script processor", TypeToken.of(TestEnum.class)));
        assertEquals(TestEnum.SCRIPT_PROCESSOR, converter.convert("script-processor", TypeToken.of(TestEnum.class)));
    }

    enum TestEnum {
        CONVERTER(1),
        SCRIPT_PROCESSOR(2),
        MAPPER(3),
        FIXTURE(4);

        private int value;

        TestEnum(int value) {
            this.value = value;
        }
    }
}