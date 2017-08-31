package io.beanmother.core.mapper;

import io.beanmother.core.fixture.FixtureList;
import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.fixture.FixtureTemplateConverter;
import io.beanmother.core.fixture.FixtureValue;
import io.beanmother.core.converter.StandardConverterFactory;
import io.beanmother.core.mapper.setter.SetterMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link SetterMapper}
 */
public class SetterMapperTest extends MapperTest {

    SetterMapper mapper = new SetterMapper(new StandardConverterFactory());
    FixtureMap map;
    SetterObject obj;

    @Before
    public void initializeFixtureMap() {
        map = new FixtureMap();
        map.setRoot(true);
        map.setFixtureName("setterObject");
        map.setParent(null);

        obj = new SetterObject();
    }

    @Test
    public void testSimpleObjectMapping() {
        mapper.map(obj, "integer", new FixtureValue(10));
        assertEquals(obj.getInteger(), new Integer(10));

        mapper.map(obj, "primitiveInt", new FixtureValue(11));
        assertEquals(obj.getPrimitiveInt(), 11);

        Date date = new Date();
        mapper.map(obj, "date", new FixtureValue(date));
        assertEquals(obj.getDate(), date);

        mapper.map(obj, "string", new FixtureValue("test"));
        assertEquals(obj.getString(), "test");
    }

    @Test
    public void testCastingAndMapping() {
        mapper.map(obj, "number", new FixtureValue(10));
        assertEquals(obj.getNumber(), new Integer(10));

        mapper.map(obj, "ploat", new FixtureValue(10));
        assertEquals(obj.getPloat(), new Float(10));
    }

    @Test
    public void testListMapping() {
        ListSetterObject listSetterObject = new ListSetterObject();
        List<String> strs = new ArrayList<>();
        strs.add("one");
        strs.add("two");
        FixtureList fixture = FixtureTemplateConverter.convert(strs, null, null);

        mapper.map(listSetterObject, "strList", fixture);

        assertEquals(listSetterObject.getStrList().size(), 2);
        assertEquals(listSetterObject.getStrList().get(0), "one");
        assertEquals(listSetterObject.getStrList().get(1), "two");
    }

    @Test
    public void testArrayMapping() {
        ListSetterObject listSetterObject = new ListSetterObject();
        List<String> strs = new ArrayList<>();
        strs.add("one");
        strs.add("two");
        FixtureList fixture = FixtureTemplateConverter.convert(strs, null, null);
        mapper.map(listSetterObject, "strArray", fixture);

        assertEquals(listSetterObject.getStrArray().length, 2);
        assertEquals(listSetterObject.getStrArray()[0], "one");
        assertEquals(listSetterObject.getStrArray()[1], "two");
    }

    @Test
    public void testNonGenericListMapping() {
        ListSetterObject listSetterObject = new ListSetterObject();
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        FixtureList fixture = FixtureTemplateConverter.convert(integers, null, null);
        mapper.map(listSetterObject, "objList", fixture);

        assertEquals(listSetterObject.getObjList().size(), 2);
        assertEquals(listSetterObject.getObjList().get(0), 1);
        assertEquals(listSetterObject.getObjList().get(1), 2);
    }

    public static class ListSetterObject {
        private List<String> strList;
        private LinkedList<Integer> integerLinkedList;
        private List objList;
        private String[] strArray;
        private int[] intArray;

        public List<String> getStrList() {
            return strList;
        }

        public void setStrList(List<String> strList) {
            this.strList = strList;
        }

        public LinkedList<Integer> getIntegerLinkedList() {
            return integerLinkedList;
        }

        public void setIntegerLinkedList(LinkedList<Integer> integerLinkedList) {
            this.integerLinkedList = integerLinkedList;
        }

        public List getObjList() {
            return objList;
        }

        public void setObjList(List objList) {
            this.objList = objList;
        }

        public String[] getStrArray() {
            return strArray;
        }

        public void setStrArray(String[] strArray) {
            this.strArray = strArray;
        }

        public int[] getIntArray() {
            return intArray;
        }

        public void setIntArray(int[] intArray) {
            this.intArray = intArray;
        }
    }

    public static class SetterObject {
        private int primitiveInt;
        private Integer integer;
        private Number number;
        private Float ploat;
        private Date date;
        private String string;
        private List<String> stringList;
        private List<List<String>> listOfStringList;
        private Map<String, String> stringMap;
        private Map<String, Map<String, String>> mapOfStringMap;
        private Map<String, List<String>> mapOfStringList;

        public int getPrimitiveInt() {
            return primitiveInt;
        }

        public void setPrimitiveInt(int primitiveInt) {
            this.primitiveInt = primitiveInt;
        }

        public Integer getInteger() {
            return integer;
        }

        public void setInteger(Integer integer) {
            this.integer = integer;
        }

        public Number getNumber() {
            return number;
        }

        public void setNumber(Number number) {
            this.number = number;
        }

        public Float getPloat() {
            return ploat;
        }

        public void setPloat(Float ploat) {
            this.ploat = ploat;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }

        public List<String> getStringList() {
            return stringList;
        }

        public void setStringList(List<String> stringList) {
            this.stringList = stringList;
        }

        public List<List<String>> getListOfStringList() {
            return listOfStringList;
        }

        public void setListOfStringList(List<List<String>> listOfStringList) {
            this.listOfStringList = listOfStringList;
        }

        public Map<String, String> getStringMap() {
            return stringMap;
        }

        public void setStringMap(Map<String, String> stringMap) {
            this.stringMap = stringMap;
        }

        public Map<String, Map<String, String>> getMapOfStringMap() {
            return mapOfStringMap;
        }

        public void setMapOfStringMap(Map<String, Map<String, String>> mapOfStringMap) {
            this.mapOfStringMap = mapOfStringMap;
        }

        public Map<String, List<String>> getMapOfStringList() {
            return mapOfStringList;
        }

        public void setMapOfStringList(Map<String, List<String>> mapOfStringList) {
            this.mapOfStringList = mapOfStringList;
        }
    }
}