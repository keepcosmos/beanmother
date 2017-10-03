package io.beanmother.joda.converter;

import io.beanmother.core.converter.ConverterModule;
import io.beanmother.core.converter.KnownConverterModuleLoader;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Test for {@link JodaTimeConverterModule}
 */
public class JodaTimeConverterModuleTest {

    @Test
    public void testLoadModule() {
        List<ConverterModule> modules = KnownConverterModuleLoader.load();
        boolean loaded = false;
        for (ConverterModule module : modules) {
            if (module instanceof JodaTimeConverterModule) {
                loaded = true;
            }
        }

        assertTrue(loaded);
    }
}