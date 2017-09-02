package io.beanmother.core.mapper.setter;

import io.beanmother.core.converter.StandardConverterFactory;
import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.fixture.FixtureTemplateConverter;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link FixtureMapSetterMapper}
 */
public class FixtureMapSetterMapperTest {

    FixtureMapSetterMapper mapper;

    @Before
    public void setup() {
        mapper = (FixtureMapSetterMapper) new SetterMapperMediator(new StandardConverterFactory()).getFixtureMapPropertyMapper();
    }

    @Test
    public void testSimpleMap() {
        MapSetterObject target = new MapSetterObject();

        Map<String, Integer> stringIntegerMap = new LinkedHashMap<>();
        stringIntegerMap.put("one", 1);
        stringIntegerMap.put("two", 2);
        FixtureMap fixture = FixtureTemplateConverter.convert(stringIntegerMap, null, null);

        mapper.map(target, "stringToInteger", fixture);

        assertEquals(2, target.getStringToInteger().size());
        assertEquals(new Integer(1), target.getStringToInteger().get("one"));
        assertEquals(new Integer(2), target.getStringToInteger().get("two"));

        Map<String, String> integerStringMap = new LinkedHashMap<>();
        integerStringMap.put("1", "one");
        integerStringMap.put("2", "two");
        fixture = FixtureTemplateConverter.convert(integerStringMap, null, null);

        mapper.map(target, "integerToString", fixture);

        assertEquals(2, target.getIntegerToString().size());
        assertEquals("one", target.getIntegerToString().get(1));
        assertEquals("two", target.getIntegerToString().get(2));
    }


    static class MapSetterObject {
        private Map<String, Integer> stringToInteger;
        private Map<Integer, String> integerToString;
        private HashMap<String, String> stringToStringLinkedHashMap;
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

        public HashMap<String, String> getStringToStringLinkedHashMap() {
            return stringToStringLinkedHashMap;
        }

        public void setStringToStringLinkedHashMap(HashMap<String, String> stringToStringLinkedHashMap) {
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

    static class Bean {
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