package io.beanmother.core.converter;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.std.SameClassConverter;
import io.beanmother.core.converter.std.StandardConverterModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ConverterFactory {

    private static Converter sameClassConverter = new SameClassConverter();

    private List<Converter> converters = new ArrayList<>();

    public ConverterFactory() {
        registerDefaultConverterModules();
    }

    public void register(ConverterModule converterModule) {
        Iterator<Converter> elements = converterModule.getConverters().iterator();
        while (elements.hasNext()) {
            converters.add(elements.next());
        }
        Collections.sort(this.converters);
    }

    public void register(Converter converter) {
        converters.add(converter);
        Collections.sort(this.converters);
    }

    public Converter get(Object source, TypeToken<?> targetTokenType) {
        for (Converter converter : converters) {
            if (converter.canHandle(source, targetTokenType)) {
                return converter;
            }
        }

        if (sameClassConverter.canHandle(source, targetTokenType)) {
            return sameClassConverter;
        }

        return null;
    }

    protected void registerDefaultConverterModules() {
        register(new StandardConverterModule());
        List<ConverterModule> knownModules = KnownConverterModuleLoader.load();
        for (ConverterModule module : knownModules) {
            register(module);
        }
    }
}
