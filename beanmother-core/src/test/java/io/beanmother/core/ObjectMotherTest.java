package io.beanmother.core;

import io.beanmother.testmodel.Author;
import org.junit.Test;

import java.util.List;

/**
 * Test for {@link ObjectMother}
 */
public class ObjectMotherTest {

    static class TestObjectMother extends AbstractBeanMother {

        private final static BeanMother beanMother = new TestObjectMother();

        private TestObjectMother() {
            super();
        }

        @Override
        public String[] defaultFixturePaths() {
            return new String[]{ "testmodel_fixtures" };
        }
    }

    @Test
    public void test() {
        List<Author> authors = TestObjectMother.beanMother.bear("unknown_author", Author.class, 10);
        System.out.println(authors);
    }
}