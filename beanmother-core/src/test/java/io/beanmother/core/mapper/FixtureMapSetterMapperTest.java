package io.beanmother.core.mapper;

import io.beanmother.core.converter.ConverterFactory;
import io.beanmother.core.common.FixtureMap;
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
public class FixtureMapSetterMapperTest {

    SetterAndFieldFixtureMapper mapper;

    @Before
    public void setup() {
        mapper = (SetterAndFieldFixtureMapper) new SetterMapperMediator(new ConverterFactory()).getFixtureMapper();
    }

    @Test
    public void testSimpleMapping() {
        Map<String, Integer> stringIntegerMap = new LinkedHashMap<>();
        stringIntegerMap.put("one", 1);
        stringIntegerMap.put("two", 2);

        FixtureMap fixture = FixtureTemplateWrapper.wrap(stringIntegerMap, null, null);
        MapSetterObject target = new MapSetterObject();
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
        MapSetterObject target = new MapSetterObject();
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
        MapSetterObject target = new MapSetterObject();
        mapper.map(target, "stringToBean", fixture);

        assertEquals(target.getStringToBean().size(), 2);
        assertEquals(target.getStringToBean().get("bean1").getId(), 1);
        assertEquals(target.getStringToBean().get("bean1").getName(), "Hemingway");
        assertEquals(target.getStringToBean().get("bean2").getId(), 2);
        assertEquals(target.getStringToBean().get("bean2").getName(), "Tolstoy");

    }


    public static class MapSetterObject {
        private Map<String, Integer> stringToInteger;
        private Map<Integer, String> integerToString;
        private LinkedHashMap<String, String> stringToStringLinkedHashMap;
        private Map<String, List<String>> stringToIntegerList;
        private Map<String, String[]> stringToStringArray;
        private Map<String, Map<String, String>> stringToStringMap;
        private Map<String, Bean> stringToBean;
        private Map noGenericMap;

        public Map<String, Integer> getStringToInteger() {
            return stringToInteger;
        }

        public void setStringToInteger(Map<String, Integer> stringToInteger) {
            this.stringToInteger = stringToInteger;
        }

        public Map<Integer, String> getIntegerToString() {
            return integerToString;
        }

        public void setIntegerToString(Map<Integer, String> integerToString) {
            this.integerToString = integerToString;
        }

        public LinkedHashMap<String, String> getStringToStringLinkedHashMap() {
            return stringToStringLinkedHashMap;
        }

        public void setStringToStringLinkedHashMap(LinkedHashMap<String, String> stringToStringLinkedHashMap) {
            this.stringToStringLinkedHashMap = stringToStringLinkedHashMap;
        }

        public Map<String, List<String>> getStringToIntegerList() {
            return stringToIntegerList;
        }

        public void setStringToIntegerList(Map<String, List<String>> stringToIntegerList) {
            this.stringToIntegerList = stringToIntegerList;
        }

        public Map<String, String[]> getStringToStringArray() {
            return stringToStringArray;
        }

        public void setStringToStringArray(Map<String, String[]> stringToStringArray) {
            this.stringToStringArray = stringToStringArray;
        }

        public Map<String, Map<String, String>> getStringToStringMap() {
            return stringToStringMap;
        }

        public void setStringToStringMap(Map<String, Map<String, String>> stringToStringMap) {
            this.stringToStringMap = stringToStringMap;
        }

        public Map<String, Bean> getStringToBean() {
            return stringToBean;
        }

        public void setStringToBean(Map<String, Bean> stringToBean) {
            this.stringToBean = stringToBean;
        }

        public Map getNoGenericMap() {
            return noGenericMap;
        }

        public void setNoGenericMap(Map noGenericMap) {
            this.noGenericMap = noGenericMap;
        }
    }

    public static class Bean {
        int id;
        String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}