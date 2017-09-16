package io.beanmother.core.converter;

import io.beanmother.core.DummyConverterModule;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Test for {@link KnownConverterModuleLoader}
 */
public class KnownConverterModuleLoaderTest {

    @Test
    public void testLoadConverterModule() {
        List<ConverterModule> modules =  KnownConverterModuleLoader.load();
        ConverterModule converterModule = null;

        for (ConverterModule module : modules) {
            if (module instanceof DummyConverterModule) {
                converterModule = module;
            }
        }

        Assert.assertNotNull(converterModule);
    }
}