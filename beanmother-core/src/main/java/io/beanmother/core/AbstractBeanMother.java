package io.beanmother.core;

import io.beanmother.core.common.FixtureMap;
import io.beanmother.core.common.FixtureMapTraversal;
import io.beanmother.core.common.FixtureValue;
import io.beanmother.core.converter.ConverterFactory;
import io.beanmother.core.loader.Location;
import io.beanmother.core.loader.store.DefaultFixturesStore;
import io.beanmother.core.loader.store.FixturesStore;
import io.beanmother.core.mapper.ConstructHelper;
import io.beanmother.core.mapper.DefaultFixtureMapper;
import io.beanmother.core.mapper.FixtureMapper;
import io.beanmother.core.postprocessor.PostProcessor;
import io.beanmother.core.postprocessor.PostProcessorFactory;
import io.beanmother.core.script.DefaultScriptHandler;
import io.beanmother.core.script.ScriptFragment;
import io.beanmother.core.script.ScriptHandler;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBeanMother implements BeanMother {

    private FixturesStore fixturesStore;

    private ConverterFactory converterFactory;

    private FixtureMapper fixtureMapper;

    private ScriptHandler scriptHandler;

    private PostProcessorFactory postProcessorFactory;

    public AbstractBeanMother() {
        fixturesStore = new DefaultFixturesStore();
        converterFactory = new ConverterFactory();
        fixtureMapper = new DefaultFixtureMapper(converterFactory);
        scriptHandler = new DefaultScriptHandler();
        postProcessorFactory = new PostProcessorFactory();

        initialize();
    }

    @Override
    public <T> T bear(String fixtureName, T target) {
        assertFixtureMapExists(fixtureName);

        FixtureMap fixtureMap = fixturesStore.reproduce(fixtureName);
        return _bear(target, fixtureMap);
    }

    @Override
    public <T> T bear(String fixtureName, Class<T> targetClass) {
        assertFixtureMapExists(fixtureName);
        FixtureMap fixtureMap = fixturesStore.reproduce(fixtureName);

        T inst = (T) ConstructHelper.construct(targetClass, fixtureMap);
        return bear(fixtureName, inst);
    }

    @Override
    public <T> List<T> bear(String fixtureName, T target, int size) {
        List<T> result = new ArrayList<>();
        for (int i = 0 ; i < size ; i++) {
            result.add(bear(fixtureName, target));
        }
        return result;
    }

    @Override
    public <T> List<T> bear(String fixtureName, Class<T> targetClass, int size) {
        List<T> result = new ArrayList<>();
        for (int i = 0 ; i < size ; i++) {
            result.add(bear(fixtureName, targetClass));
        }
        return result;
    }

    @Override
    public BeanMother addFixtureLocation(String path) {
        fixturesStore.addLocation(new Location(path));
        return this;
    }

    public String[] defaultFixturePaths() {
        return new String[] { "fixtures" };
    }

    protected void configureConverterFactory(ConverterFactory converterFactory) {
        // Do nothing.
    }

    protected void configureScriptHandler(ScriptHandler scriptHandler) {
        // Do nothing.
    }

    protected void configurePostProcessorFactory(PostProcessorFactory postProcessorFactory) {
        // Do nothing.
    }

    protected void initialize() {
        for ( String path : defaultFixturePaths()) {
            this.fixturesStore.addLocation(new Location(path));
        }
        configureConverterFactory(converterFactory);
        configureScriptHandler(scriptHandler);
        configurePostProcessorFactory(postProcessorFactory);
    }

    private <T> T _bear(T target, FixtureMap fixtureMap) {
        FixtureMapTraversal.traverse(fixtureMap, new FixtureMapTraversal.Processor() {
            @Override
            public void visit(FixtureValue edge) {
                if (ScriptFragment.isScript(edge)) {
                    scriptHandler.runScript(edge);
                }
            }
        });

        fixtureMapper.map(fixtureMap, target);

        List<PostProcessor<T>> postProcessors = postProcessorFactory.get((Class<T>) target.getClass());
        for (PostProcessor<T> postProcessor : postProcessors) {
            postProcessor.process(target, fixtureMap);
        }

        return target;
    }

    private void assertFixtureMapExists(String fixtureName) {
        if (!fixturesStore.exists(fixtureName)) {
            throw new IllegalArgumentException("can not find " + fixtureName);
        }
    }
}