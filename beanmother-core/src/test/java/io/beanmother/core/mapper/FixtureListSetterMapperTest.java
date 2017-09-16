package io.beanmother.core.mapper;

import io.beanmother.core.converter.ConverterFactory;
import io.beanmother.core.common.FixtureList;
import io.beanmother.core.loader.FixtureTemplateWrapper;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link SetterAndFieldFixtureMapper}
 */
public class FixtureListSetterMapperTest {

    SetterAndFieldFixtureMapper mapper;

    @Before
    public void setup() {
        mapper = (SetterAndFieldFixtureMapper) new SetterMapperMediator(new ConverterFactory()).getFixtureMapper();
    }

    @Test
    public void testListMapping() {
        ListSetterObject target = new ListSetterObject();
        List<String> strList = new ArrayList<>();
        strList.add("one");
        strList.add("two");
        FixtureList fixture = FixtureTemplateWrapper.wrap(strList, null, null);

        mapper.map(target, "strList", fixture);

        assertEquals(2, target.getStrList().size());
        assertEquals("one", target.getStrList().get(0));
        assertEquals("two", target.getStrList().get(1));

        mapper.map(target, "strLinkedList", fixture);
        assertEquals(2, target.getStrLinkedList().size());
        assertEquals("one", target.getStrLinkedList().get(0));
        assertEquals("two", target.getStrLinkedList().get(1));
    }

    @Test
    public void testNonGenericListMapping() {
        ListSetterObject target = new ListSetterObject();
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        FixtureList fixture = FixtureTemplateWrapper.wrap(integers, null, null);
        mapper.map(target, "objList", fixture);

        assertEquals(2, target.getObjList().size());
        assertEquals(1, target.getObjList().get(0));
        assertEquals(2, target.getObjList().get(1));
    }

    @Test
    public void testArrayMapping() {
        ListSetterObject target = new ListSetterObject();
        List<String> strList = new ArrayList<>();
        strList.add("one");
        strList.add("two");
        FixtureList fixture = FixtureTemplateWrapper.wrap(strList, null, null);
        mapper.map(target, "strArray", fixture);

        assertEquals(2, target.getStrArray().length);
        assertEquals("one", target.getStrArray()[0]);
        assertEquals("two", target.getStrArray()[1]);
    }

    @Test
    public void testPrimitiveArrayMapping() {
        ListSetterObject target = new ListSetterObject();
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        FixtureList fixture = FixtureTemplateWrapper.wrap(integers, null, null);

        mapper.map(target, "intArray", fixture);
        assertEquals(2, target.getIntArray().length);
        assertEquals(1, target.getIntArray()[0]);
        assertEquals(2, target.getIntArray()[1]);
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

        FixtureList fixture = FixtureTemplateWrapper.wrap(samples, null, null);
        ListSetterObject target = new ListSetterObject();

        mapper.map(target, "sampleList", fixture);
        assertEquals(2, target.getSampleList().size());
        assertEquals(1, target.getSampleList().get(0).getId());
        assertEquals("Hemingway", target.getSampleList().get(0).getName());
        assertEquals(2, target.getSampleList().get(1).getId());
        assertEquals("Tolstoy", target.getSampleList().get(1).getName());

        mapper.map(target, "sampleArray", fixture);
        assertEquals(2, target.getSampleArray().length);
        assertEquals(1, target.getSampleArray()[0].getId());
        assertEquals("Hemingway", target.getSampleArray()[0].getName());
        assertEquals(2, target.getSampleArray()[1].getId());
        assertEquals("Tolstoy", target.getSampleArray()[1].getName());
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

        FixtureList fixture = FixtureTemplateWrapper.wrap(strList, null, null);
        ListSetterObject target = new ListSetterObject();

        mapper.map(target, "listOfList", fixture);
        assertEquals(2, target.getListOfList().size());
        assertEquals("a", target.getListOfList().get(0).get(0));
        assertEquals("b", target.getListOfList().get(0).get(1));
        assertEquals("c", target.getListOfList().get(1).get(0));
        assertEquals("d", target.getListOfList().get(1).get(1));

        mapper.map(target, "listOfArray", fixture);
        assertEquals(2, target.getListOfArray().size());
        assertEquals("a", target.getListOfArray().get(0)[0]);
        assertEquals("b", target.getListOfArray().get(0)[1]);
        assertEquals("c", target.getListOfArray().get(1)[0]);
        assertEquals("d", target.getListOfArray().get(1)[1]);

        mapper.map(target, "arrayOfList", fixture);
        assertEquals(2, target.getArrayOfList().length);
        assertEquals("a", target.getArrayOfList()[0].get(0));
        assertEquals("b", target.getArrayOfList()[0].get(1));
        assertEquals("c", target.getArrayOfList()[1].get(0));
        assertEquals("d", target.getArrayOfList()[1].get(1));
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

    public static class Sample {
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