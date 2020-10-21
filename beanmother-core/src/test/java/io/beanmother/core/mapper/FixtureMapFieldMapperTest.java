package io.beanmother.core.mapper;

import io.beanmother.core.common.FixtureMap;
import io.beanmother.core.converter.ConverterFactory;
import io.beanmother.core.loader.FixtureTemplateWrapper;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link SetterAndFieldFixtureMapper}
 */
public class FixtureMapFieldMapperTest {

    SetterAndFieldFixtureMapper mapper;

    @Before
    public void setup() {
        mapper = (SetterAndFieldFixtureMapper) new MapperMediatorImpl(new ConverterFactory()).getFixtureMapper();
    }

    @Test
    public void testSimpleMapping() {
        Map<String, Integer> stringIntegerMap = new LinkedHashMap<>();
        stringIntegerMap.put("one", 1);
        stringIntegerMap.put("two", 2);

        FixtureMap fixture = FixtureTemplateWrapper.wrap(stringIntegerMap, null, null);
        FixtureMapSetterMapperTest.MapSetterObject target = new FixtureMapSetterMapperTest.MapSetterObject();
        mapper.map(target, "stringToInteger", fixture);

        assertEquals(2, target.getStringToInteger().size());
        assertEquals(new Integer(1), target.getStringToInteger().get("one"));
        assertEquals(new Integer(2), target.getStringToInteger().get("two"));

        mapper.map(target, "noGenericMap", fixture);
        assertEquals(2, target.getNoGenericMap().size());
        assertEquals(new Integer(1), target.getNoGenericMap().get("one"));
        assertEquals(new Integer(2), target.getNoGenericMap().get("two"));

        mapper.map(target, "stringToStringLinkedHashMap", fixture);
        assertEquals(2, target.getStringToStringLinkedHashMap().size());
        assertEquals("1", target.getStringToStringLinkedHashMap().get("one"));
        assertEquals("2", target.getStringToStringLinkedHashMap().get("two"));
    }

    @Test
    public void testNonStringKeyMapping() {
        Map<String, String> integerStringMap = new LinkedHashMap<>();
        integerStringMap.put("1", "one");
        integerStringMap.put("2", "two");

        FixtureMap fixture = FixtureTemplateWrapper.wrap(integerStringMap, null, null);
        FixtureMapSetterMapperTest.MapSetterObject target = new FixtureMapSetterMapperTest.MapSetterObject();
        mapper.map(target, "integerToString", fixture);

        assertEquals(2, target.getIntegerToString().size());
        assertEquals("one", target.getIntegerToString().get(1));
        assertEquals("two", target.getIntegerToString().get(2));
    }


    @Test
    public void testBeanMapping(){
        Map<String, Object> beanMap = new LinkedHashMap<>();

        Map<String, Object> bean1 = new LinkedHashMap<>();
        bean1.put("id", 1);
        bean1.put("name", "Hemingway");
        beanMap.put("bean1", bean1);

        Map<String, Object> bean2 = new LinkedHashMap<>();
        bean2.put("id", 2);
        bean2.put("name", "Tolstoy");
        beanMap.put("bean2", bean2);

        FixtureMap fixture = FixtureTemplateWrapper.wrap(beanMap, null, null);
        FixtureMapSetterMapperTest.MapSetterObject target = new FixtureMapSetterMapperTest.MapSetterObject();
        mapper.map(target, "stringToBean", fixture);

        assertEquals(2, target.getStringToBean().size());
        assertEquals(1, target.getStringToBean().get("bean1").getId());
        assertEquals("Hemingway", target.getStringToBean().get("bean1").getName());
        assertEquals(2, target.getStringToBean().get("bean2").getId());
        assertEquals("Tolstoy", target.getStringToBean().get("bean2").getName());

    }


    public static class MapSetterObject {
        public Map<String, Integer> stringToInteger;
        public Map<Integer, String> integerToString;
        public LinkedHashMap<String, String> stringToStringLinkedHashMap;
        public Map<String, List<String>> stringToIntegerList;
        public Map<String, String[]> stringToStringArray;
        public Map<String, Map<String, String>> stringToStringMap;
        public Map<String, FixtureMapSetterMapperTest.Bean> stringToBean;
        public Map noGenericMap;
    }

    public static class Bean {
        public int id;
        public String name;
    }
}
