package io.beanmother.core.mapper.setter;

import io.beanmother.core.converter.StandardConverterFactory;
import io.beanmother.core.fixture.FixtureList;
import io.beanmother.core.fixture.FixtureTemplateConverter;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link FixtureListSetterMapper}
 */
public class FixtureListSetterMapperTest {

    FixtureListSetterMapper mapper;

    @Before
    public void setup() {
        mapper = (FixtureListSetterMapper) new SetterMapperMediator(new StandardConverterFactory()).getFixtureListPropertyMapper();
    }

    @Test
    public void testListMapping() {
        ListSetterObject target = new ListSetterObject();
        List<String> strs = new ArrayList<>();
        strs.add("one");
        strs.add("two");
        FixtureList fixture = FixtureTemplateConverter.convert(strs, null, null);

        mapper.map(target, "strList", fixture);

        assertEquals(target.getStrList().size(), 2);
        assertEquals(target.getStrList().get(0), "one");
        assertEquals(target.getStrList().get(1), "two");

        mapper.map(target, "strLinkedList", fixture);
        assertEquals(target.getStrLinkedList().size(), 2);
        assertEquals(target.getStrLinkedList().get(0), "one");
        assertEquals(target.getStrLinkedList().get(1), "two");
    }

    @Test
    public void testNonGenericListMapping() {
        ListSetterObject target = new ListSetterObject();
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        FixtureList fixture = FixtureTemplateConverter.convert(integers, null, null);
        mapper.map(target, "objList", fixture);

        assertEquals(target.getObjList().size(), 2);
        assertEquals(target.getObjList().get(0), 1);
        assertEquals(target.getObjList().get(1), 2);
    }

    @Test
    public void testArrayMapping() {
        ListSetterObject target = new ListSetterObject();
        List<String> strs = new ArrayList<>();
        strs.add("one");
        strs.add("two");
        FixtureList fixture = FixtureTemplateConverter.convert(strs, null, null);
        mapper.map(target, "strArray", fixture);

        assertEquals(target.getStrArray().length, 2);
        assertEquals(target.getStrArray()[0], "one");
        assertEquals(target.getStrArray()[1], "two");
    }

    @Test
    public void testPrimitiveArrayMapping() {
        ListSetterObject target = new ListSetterObject();
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        FixtureList fixture = FixtureTemplateConverter.convert(integers, null, null);

        mapper.map(target, "intArray", fixture);
        assertEquals(target.getIntArray().length, 2);
        assertEquals(target.getIntArray()[0], 1);
        assertEquals(target.getIntArray()[1], 2);
    }

    @Test
    public void testObjectListAndArrayMapping() {
        Map<String, Object> sample1 = new HashMap<>();
        sample1.put("id", 1);
        sample1.put("name", "Hemingway");
        Map<String, Object> sample2 = new HashMap<>();
        sample2.put("id", 2);
        sample2.put("name", "Tolstoy");

        List<Map<String, Object>> samples = new ArrayList<>();
        samples.add(sample1);
        samples.add(sample2);

        FixtureList fixture = FixtureTemplateConverter.convert(samples, null, null);
        ListSetterObject target = new ListSetterObject();

        mapper.map(target, "sampleList", fixture);
        assertEquals(target.getSampleList().size(), 2);
        assertEquals(target.getSampleList().get(0).getId(), 1);
        assertEquals(target.getSampleList().get(0).getName(), "Hemingway");
        assertEquals(target.getSampleList().get(1).getId(), 2);
        assertEquals(target.getSampleList().get(1).getName(), "Tolstoy");

        mapper.map(target, "sampleArray", fixture);
        assertEquals(target.getSampleArray().length, 2);
        assertEquals(target.getSampleArray()[0].getId(), 1);
        assertEquals(target.getSampleArray()[0].getName(), "Hemingway");
        assertEquals(target.getSampleArray()[1].getId(), 2);
        assertEquals(target.getSampleArray()[1].getName(), "Tolstoy");
    }

    @Test
    public void testNestListOrArray() {
        List<List<String>> strList = new ArrayList<>();
        List<String> nested1 = new ArrayList<>();
        nested1.add("a");
        nested1.add("b");
        List<String> nested2 = new ArrayList<>();
        nested2.add("c");
        nested2.add("d");
        strList.add(nested1);
        strList.add(nested2);

        FixtureList fixture = FixtureTemplateConverter.convert(strList, null, null);
        ListSetterObject target = new ListSetterObject();

        mapper.map(target, "listOfList", fixture);
        assertEquals(target.getListOfList().size(), 2);
        assertEquals(target.getListOfList().get(0).get(0), "a");
        assertEquals(target.getListOfList().get(0).get(1), "b");
        assertEquals(target.getListOfList().get(1).get(0), "c");
        assertEquals(target.getListOfList().get(1).get(1), "d");

        mapper.map(target, "listOfArray", fixture);
        assertEquals(target.getListOfArray().size(), 2);
        assertEquals(target.getListOfArray().get(0)[0], "a");
        assertEquals(target.getListOfArray().get(0)[1], "b");
        assertEquals(target.getListOfArray().get(1)[0], "c");
        assertEquals(target.getListOfArray().get(1)[1], "d");

        mapper.map(target, "arrayOfList", fixture);
        assertEquals(target.getArrayOfList().length, 2);
        assertEquals(target.getArrayOfList()[0].get(0), "a");
        assertEquals(target.getArrayOfList()[0].get(1), "b");
        assertEquals(target.getArrayOfList()[1].get(0), "c");
        assertEquals(target.getArrayOfList()[1].get(1), "d");
    }

    public static class ListSetterObject {
        private List<String> strList;
        private LinkedList<String> strLinkedList;
        private List objList;
        private String[] strArray;
        private int[] intArray;
        private List<Sample> sampleList;
        private Sample[] sampleArray;
        private List<List<String>> listOfList;
        private List<String[]> listOfArray;
        private List<String>[] arrayOfList;

        public List<List<String>> getListOfList() {
            return listOfList;
        }

        public void setListOfList(List<List<String>> listOfList) {
            this.listOfList = listOfList;
        }

        public List<String[]> getListOfArray() {
            return listOfArray;
        }

        public void setListOfArray(List<String[]> listOfArray) {
            this.listOfArray = listOfArray;
        }

        public List<String>[] getArrayOfList() {
            return arrayOfList;
        }

        public void setArrayOfList(List<String>[] arrayOfList) {
            this.arrayOfList = arrayOfList;
        }

        public List<Sample> getSampleList() {
            return sampleList;
        }

        public void setSampleList(List<Sample> sampleList) {
            this.sampleList = sampleList;
        }

        public Sample[] getSampleArray() {
            return sampleArray;
        }

        public void setSampleArray(Sample[] sampleArray) {
            this.sampleArray = sampleArray;
        }

        public List<String> getStrList() {
            return strList;
        }

        public void setStrList(List<String> strList) {
            this.strList = strList;
        }

        public LinkedList<String> getStrLinkedList() {
            return strLinkedList;
        }

        public void setStrLinkedList(LinkedList<String> strLinkedList) {
            this.strLinkedList = strLinkedList;
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

    static class Sample {
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