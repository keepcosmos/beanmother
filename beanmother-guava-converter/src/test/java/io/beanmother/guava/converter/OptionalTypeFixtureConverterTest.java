package io.beanmother.guava.converter;

import com.google.common.base.Optional;
import com.google.common.reflect.TypeToken;
import io.beanmother.core.common.FixtureTemplate;
import io.beanmother.core.converter.ConverterFactory;
import io.beanmother.core.loader.FixtureTemplateWrapper;
import io.beanmother.core.mapper.DefaultFixtureMapper;
import io.beanmother.core.mapper.FixtureConverter;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link OptionalTypeFixtureConverter}
 *
 * this class is loaded automatically in {@link io.beanmother.core.mapper.FixtureConverterImpl}
 *
 */
public class OptionalTypeFixtureConverterTest {
    FixtureConverter converter = new DefaultFixtureMapper(new ConverterFactory()).getFixtureConverter();

    @Test
    public void testOptionalMapping() {
        Map<String, Object> map = new HashMap<>();
        map.put("simple", 1);
        map.put("optionalString", "testString");

        Map<String, String> beanMap = new HashMap<>();
        beanMap.put("name", "testName");
        map.put("optionalBean", beanMap);

        FixtureTemplate fixture = FixtureTemplateWrapper.wrap(map, null, null);

        OptionalTest result = (OptionalTest) converter.convert(fixture, TypeToken.of(OptionalTest.class));

        assertEquals(1, result.simple.get());
        assertEquals("testString", result.optionalString.get());
        assertEquals("testName", result.optionalBean.get().name.get());
    }

    public static class OptionalTest {
        public Optional simple;
        public Optional<String> optionalString;
        public Optional<Bean> optionalBean;
    }

    public static class Bean {
        public Optional<String> name;
    }
}