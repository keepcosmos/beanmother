package io.beanmother.core.mapper;

import io.beanmother.core.common.FixtureList;
import io.beanmother.core.converter.ConverterFactory;
import io.beanmother.core.loader.FixtureTemplateWrapper;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link SetterAndFieldFixtureMapper}
 */
public class FixtureListFieldMapperTest {
    SetterAndFieldFixtureMapper mapper;

    @Before
    public void setup() {
        mapper = (SetterAndFieldFixtureMapper) new SetterMapperMediator(new ConverterFactory()).getFixtureMapper();
    }

    @Test
    public void testListMapping() {
        FixtureListSetterMapperTest.ListSetterObject target = new FixtureListSetterMapperTest.ListSetterObject();
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
        FixtureListSetterMapperTest.ListSetterObject target = new FixtureListSetterMapperTest.ListSetterObject();
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
        FixtureListSetterMapperTest.ListSetterObject target = new FixtureListSetterMapperTest.ListSetterObject();
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
        FixtureListSetterMapperTest.ListSetterObject target = new FixtureListSetterMapperTest.ListSetterObject();
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
        FixtureListSetterMapperTest.ListSetterObject target = new FixtureListSetterMapperTest.ListSetterObject();

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
        FixtureListSetterMapperTest.ListSetterObject target = new FixtureListSetterMapperTest.ListSetterObject();

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
        public List<String> strList;
        public LinkedList<String> strLinkedList;
        public List objList;
        public String[] strArray;
        public int[] intArray;
        public List<FixtureListSetterMapperTest.Sample> sampleList;
        public FixtureListSetterMapperTest.Sample[] sampleArray;
        public List<List<String>> listOfList;
        public List<String[]> listOfArray;
        public List<String>[] arrayOfList;
    }

    public static class Sample {
        public int id;
        public String name;
    }
}
