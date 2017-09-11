package io.beanmother.core;

import io.beanmother.testmodel.Author;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.List;

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
            return new String[]{"testmodel_fixtures"};
        }
    }

    TestObjectMother beanMother = TestObjectMother.getInstance();

    @Test
    public void testMapping() throws URISyntaxException {
        List<Author> authors = beanMother.bear("unknown_author", Author.class, 5);
        System.out.println(authors);
    }
}