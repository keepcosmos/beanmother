package io.beanmother.core.mapper;

import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.mapper.converter.ConverterFactory;
import io.beanmother.testmodel.Person;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Test for {@link SetterMapper}
 */
public class SetterMapperTest extends MapperTest {

    SetterMapper setterMapper;

    @Before
    public void setup() throws IOException {
        setterMapper = new SetterMapper(new ConverterFactory());
    }

    @Test
    public void testSetterMapping() throws IOException {
        FixtureMap hemingwayMap = getFixturesStore().get("hemingway");
        Person hemingway = new Person();

//        for (Map.Entry<String, Object> entry : hemingwayMap.entrySet()) {
//            setterMapper.map(hemingway, entry.getKey(), entry.getValue());
//        }
//
//        assertEquals(hemingway.getId(), hemingwayMap.get("id"));
//        assertEquals(hemingway.getName(), hemingwayMap.get("name"));
//        assertEquals(hemingway.getBirth(), hemingwayMap.get("birth"));
    }
}