package io.beanmother.core.mapper;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * Test for {@link DefaultFixtureMapper}
 */
@Ignore
public class DefaultFixtureMapperTest {

    DefaultFixtureMapper fixtureMapper;

    @Before
    public void setup() {
        fixtureMapper = new DefaultFixtureMapper();
    }


    @Test(expected = NoSuchMethodException.class)
    public void testExceptionWithoutNoArgumentConstructorClass() throws NoSuchMethodException {
//        fixtureMapper.map(new FixtureMap("test"), ArgumentConstructorClass.class);
    }

    @Test
    public void testMap() throws InvocationTargetException, IllegalAccessException {
//        FixtureMap fixtureMap = new FixtureMap("test");
//        fixtureMap.put("id", 1);
//        fixtureMap.put("name", "Jessy");
//
//        ObjectMapper mapper = new ObjectMapper();
//        Person person = mapper.convertValue(fixtureMap, Person.class);

//        Person person = fixtureMapper.map(fixtureMap, Person.class);
//
//        Assert.assertEquals(person.getId(), 1);
//        Assert.assertEquals(person.getName(), "Jessy");
    }

    public void ttt(Object a) {
        Class t = a.getClass();
        System.out.println(t);
    }

    static class ArgumentConstructorClass {
        public ArgumentConstructorClass(String any) { }
    }
}