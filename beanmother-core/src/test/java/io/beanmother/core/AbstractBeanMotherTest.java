package io.beanmother.core;

import org.junit.Ignore;
import org.junit.Test;

public class AbstractBeanMotherTest {

    static class TestObjectMother extends AbstractBeanMother {

        private final static TestObjectMother beanMother = new AbstractBeanMotherTest.TestObjectMother();

        public static TestObjectMother getInstance() {
            return beanMother;
        }

        private TestObjectMother() {
            super();
        }

        @Override
        public String[] defaultFixturePaths() {
            return new String[]{ "testmodel_fixtures" };
        }
    }

    TestObjectMother beanMother = TestObjectMother.getInstance();

    @Test
    @Ignore
    public void testMapping() {
//        Author author = beanMother.bear("hemingway", Author.class);
//        System.out.println(author);

//        List<Author> authors = beanMother.bear("unknown_author", Author.class, 5);
//        System.out.println(authors);
    }
}