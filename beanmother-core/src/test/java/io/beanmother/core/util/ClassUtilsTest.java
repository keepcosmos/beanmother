package io.beanmother.core.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link ClassUtils}
 */
public class ClassUtilsTest {

    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    public void testClassLoader() {
        assertEquals(ClassUtils.getDefaultClassLoader(), classLoader);
    }

}