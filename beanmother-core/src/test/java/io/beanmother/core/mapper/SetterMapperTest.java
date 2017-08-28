package io.beanmother.core.mapper;

import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.fixture.FixtureValue;
import io.beanmother.core.mapper.converter.StandardConverterFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public void testSimpleObject() {
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
    public void testCastingSimpleJavaObject() {
        mapper.map(obj, "number", new FixtureValue(10));
        assertEquals(obj.getNumber(), new Integer(10));

        mapper.map(obj, "ploat", new FixtureValue(10));
        assertEquals(obj.getPloat(), new Float(10));
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