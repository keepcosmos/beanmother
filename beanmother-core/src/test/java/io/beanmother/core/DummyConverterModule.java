package io.beanmother.core;

import io.beanmother.core.converter.Converter;
import io.beanmother.core.converter.ConverterModule;

import java.util.Set;

/**
 * A dummy class for testing {@link io.beanmother.core.converter.KnownConverterModuleLoader}
 */
public class DummyConverterModule implements ConverterModule {
    @Override
    public Set<Converter> getConverters() {
        return null;
    }
}
