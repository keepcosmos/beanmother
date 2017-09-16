package io.beanmother.core.postprocessor;

import io.beanmother.core.common.FixtureMap;
import io.beanmother.testmodel.Author;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link PostProcessor}
 */
public class PostProcessorTest {

    @Test
    public void testPriority() {
        PostProcessor<Object> postProcessor = new PostProcessor<Object>() {
            @Override
            public void process(Object bean, FixtureMap fixtureMap) { }
        };

        assertEquals(PostProcessor.DEFAULT_PRIORITY, postProcessor.getPriority());

        postProcessor = new PostProcessor<Object>(3) {
            @Override
            public void process(Object bean, FixtureMap fixtureMap) { }
        };
        assertEquals(3, postProcessor.getPriority());
    }

    @Test
    public void testProcess() {
        Author author = new Author();
        author.setId(1);

        PostProcessor<Author> postProcessor = new PostProcessor<Author>() {
            @Override
            public void process(Author bean, FixtureMap fixtureMap) {
                bean.setId(9);
            }
        };

        postProcessor.process(author, null);

        assertEquals(9, author.getId());
    }
}