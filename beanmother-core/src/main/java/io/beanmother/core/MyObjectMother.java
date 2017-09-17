package io.beanmother.core;

import io.beanmother.core.converter.ConverterFactory;
import io.beanmother.core.postprocessor.PostProcessorFactory;
import io.beanmother.core.script.ScriptHandler;

public class MyObjectMother extends AbstractBeanMother {

    private static MyObjectMother myObjectMother = new MyObjectMother();

    private MyObjectMother() {
        super();
    }

    @Override
    public String[] defaultFixturePaths() {
        return null;
    }

    @Override
    protected void configureConverterFactory(ConverterFactory converterFactory) {
//        converterFactory.register();
    }

    @Override
    protected void configureScriptHandler(ScriptHandler scriptHandler) {
//        scriptHandler.register();
    }

    @Override
    protected void configurePostProcessorFactory(PostProcessorFactory postProcessorFactory) {
//        this.addFixtureLocation()
    }
}
