package io.beanmother.core.postprocessor;

import io.beanmother.core.common.FixtureMap;
import io.beanmother.testmodel.Author;
import io.beanmother.testmodel.Book;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link PostProcessorFactory}
 */
public class PostProcessorFactoryTest {

    @Test
    public void testRegisterAndGet() {
        PostProcessorFactory factory = new PostProcessorFactory();

        PostProcessor<Author> authorPostProcessor = new PostProcessor<Author>() {
            @Override
            public void process(Author bean, FixtureMap fixtureMap) { }
        };

        assertTrue(factory.get(Author.class).isEmpty());
        factory.register(authorPostProcessor);
        assertEquals(authorPostProcessor, factory.get(Author.class).get(0));

        PostProcessor<Book> bookPostProcessor = new PostProcessor<Book>() {
            @Override
            public void process(Book bean, FixtureMap fixtureMap) { }
        };

        assertTrue(factory.get(Book.class).isEmpty());
        factory.register(bookPostProcessor);
        assertEquals(bookPostProcessor, factory.get(Book.class).get(0));
    }

    @Test
    public void testSortedProcessorsGet() {
        PostProcessorFactory factory = new PostProcessorFactory();

        PostProcessor<Author> authorPostProcessor1 = new PostProcessor<Author>(1) {
            @Override
            public void process(Author bean, FixtureMap fixtureMap) { }
        };

        PostProcessor<Author> authorPostProcessor2 = new PostProcessor<Author>(2) {
            @Override
            public void process(Author bean, FixtureMap fixtureMap) { }
        };

        PostProcessor<Author> authorPostProcessor3 = new PostProcessor<Author>(3) {
            @Override
            public void process(Author bean, FixtureMap fixtureMap) { }
        };

        PostProcessor<Author> authorPostProcessor4 = new PostProcessor<Author>(4) {
            @Override
            public void process(Author bean, FixtureMap fixtureMap) { }
        };

        factory.register(authorPostProcessor3);
        factory.register(authorPostProcessor2);
        factory.register(authorPostProcessor4);
        factory.register(authorPostProcessor1);

        List<PostProcessor<Author>> processors = factory.get(Author.class);

        assertEquals(authorPostProcessor1, processors.get(0));
        assertEquals(authorPostProcessor2, processors.get(1));
        assertEquals(authorPostProcessor3, processors.get(2));
        assertEquals(authorPostProcessor4, processors.get(3));
    }
}