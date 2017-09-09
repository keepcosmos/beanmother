package io.beanmother.core;

import io.beanmother.testmodel.Author;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class AbstractBeanMotherTest {

    BeanMother beanMother = new AbstractBeanMother() {};

    @Before
    public void setup() {
        // path from beanmother-testmodel module
        beanMother.addFixtureLocation("testmodel_fixtures");
    }

    @Test
    public void testMapping() {
//        Author author = beanMother.bear("hemingway", Author.class);
//        System.out.println(author);

        List<Author> authors = beanMother.bear("unknown_author", Author.class, 5);
        System.out.println(authors);
    }
}