package io.beanmother.core;

import io.beanmother.core.converter.ConverterFactory;
import io.beanmother.core.script.ScriptRunner;

public class ObjectMother extends AbstractBeanMother {

    private final static BeanMother beanMother = new ObjectMother();

    public static BeanMother getInstance() {
        return beanMother;
    }

    private ObjectMother() {
        super();
    }

    @Override
    public String[] defaultFixturePaths() {
        return super.defaultFixturePaths();
    }

    @Override
    protected void configureConverterFactory(ConverterFactory converterFactory) {
        // Do nothing.
    }

    @Override
    protected void configureScriptRunner(ScriptRunner scriptRunner) {
        // Do nothing.
    }
}
