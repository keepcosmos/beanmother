package io.beanmother.java8.converter;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.Converter;
import io.beanmother.core.converter.ConverterFactory;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.OptionalLong;

import static org.junit.Assert.assertNotNull;

/**
 * Test auto loading in {@link io.beanmother.core.converter.ConverterFactory}
 */
public class AutoLoadTest {

    @Test
    public void autoloadJavaTimeConverters() {
        ConverterFactory converterFactory = new ConverterFactory();
        Converter converter = converterFactory.get(new Date(), TypeToken.of(LocalDateTime.class));
        assertNotNull(converter);
    }

    @Test
    public void autoloadJavaOptionalConverters() {
        ConverterFactory converterFactory = new ConverterFactory();
        Converter converter = converterFactory.get(1, TypeToken.of(OptionalLong.class));
        assertNotNull(converter);
    }

    @Test
    public void OptionalTest() {

    }
}
