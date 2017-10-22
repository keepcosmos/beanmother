package io.beanmother.guava.converter;

import com.google.common.base.Optional;
import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.Converter;
import io.beanmother.core.converter.ConverterFactory;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Test auto loading in {@link io.beanmother.core.converter.ConverterFactory}
 */
public class AutoLoadTest {

    @Test
    public void autoloadGuavaOptionalConverters() {
        ConverterFactory converterFactory = new ConverterFactory();
        Converter converter = converterFactory.get(1, new TypeToken<Optional<Integer>>(){});
        assertNotNull(converter);
    }
}
