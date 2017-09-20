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
import io.beanmother.core.mapper.FixtureConverter;
import io.beanmother.core.mapper.FixtureMapper;
import io.beanmother.core.postprocessor.PostProcessor;
import io.beanmother.core.postprocessor.PostProcessorFactory;
import io.beanmother.core.script.DefaultScriptHandler;
import io.beanmother.core.script.ScriptFragment;
import io.beanmother.core.script.ScriptHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AbstractBeanMother implements BeanMother {

    private FixturesStore fixturesStore;

    private ConverterFactory converterFactory;

    private FixtureMapper fixtureMapper;

    private FixtureConverter fixtureConverter;

    private ScriptHandler scriptHandler;

    private PostProcessorFactory postProcessorFactory;

    protected AbstractBeanMother() {
        fixturesStore = new DefaultFixturesStore();
        converterFactory = new ConverterFactory();
        fixtureMapper = new DefaultFixtureMapper(converterFactory);
        fixtureConverter = ((DefaultFixtureMapper) fixtureMapper).getFixtureConverter();
        scriptHandler = new DefaultScriptHandler();
        postProcessorFactory = new PostProcessorFactory();

        initialize();
    }

    @Override
    public <T> T bear(String fixtureName, T target) {
        return bear(fixtureName, target, null);
    }

    @Override
    public <T> T bear(String fixtureName, Class<T> targetClass) {
        FixtureMap fixtureMap = fixturesStore.reproduce(fixtureName);
        T inst = (T) ConstructHelper.construct(targetClass, fixtureMap, fixtureConverter);
        return _bear(inst, fixtureMap,null);
    }

    @Override
    public <T> T bear(String fixtureName, T target, PostProcessor<T> postProcessor) {
        FixtureMap fixtureMap = fixturesStore.reproduce(fixtureName);
        return _bear(target, fixtureMap, postProcessor);
    }

    @Override
    public <T> T bear(String fixtureName, Class<T> targetClass, PostProcessor<T> postProcessor) {
        FixtureMap fixtureMap = fixturesStore.reproduce(fixtureName);
        T inst = (T) ConstructHelper.construct(targetClass, fixtureMap, fixtureConverter);
        return _bear(inst, fixtureMap, postProcessor);
    }

    @Override
    public <T> List<T> bear(String fixtureName, Class<T> targetClass, int size) {
        return bear(fixtureName, targetClass, size, null);
    }

    @Override
    public <T> List<T> bear(String fixtureName, Class<T> targetClass, int size, PostProcessor<T> postProcessor) {
        List<T> result = new ArrayList<>();
        for (int i = 0 ; i < size ; i++) {
            result.add(bear(fixtureName, targetClass, postProcessor));
        }
        return result;
    }

    @Override
    public BeanMother addFixtureLocation(String path) {
        fixturesStore.addLocation(new Location(path));
        return this;
    }

    private <T> T _bear(T target, FixtureMap fixtureMap, PostProcessor<T> postProcessor) {
        handleScriptFixtureValue(fixtureMap);

        fixtureMapper.map(fixtureMap, target);

        List<PostProcessor<T>> postProcessors = postProcessorFactory.get((Class<T>) target.getClass());
        if (postProcessor != null) {
            postProcessors.add(postProcessor);
            Collections.sort(postProcessors);
        }
        for (PostProcessor<T> pp : postProcessors) {
            pp.process(target, fixtureMap);
        }

        return target;
    }

    protected String[] defaultFixturePaths() {
        return new String[] { "fixtures" };
    }

    /**
     * Configure the ConverterFactory
     */
    protected void configureConverterFactory(ConverterFactory converterFactory) {
        // Do nothing.
    }

    /**
     * Configure the ScriptHandler
     */
    protected void configureScriptHandler(ScriptHandler scriptHandler) {
        // Do nothing.
    }

    /**
     * Configure the PostProcessorFactory
     */
    protected void configurePostProcessorFactory(PostProcessorFactory postProcessorFactory) {
        // Do nothing.
    }

    /**
     * Initialize beanmother
     */
    protected void initialize() {
        for ( String path : defaultFixturePaths()) {
            this.fixturesStore.addLocation(new Location(path));
        }
        configureConverterFactory(converterFactory);
        configureScriptHandler(scriptHandler);
        configurePostProcessorFactory(postProcessorFactory);
    }

    private void handleScriptFixtureValue(FixtureMap fixtureMap) {
        FixtureMapTraversal.traverse(fixtureMap, new FixtureMapTraversal.Processor() {
            @Override
            public void visit(FixtureValue edge) {
                if (ScriptFragment.isScript(edge)) {
                    ScriptFragment scriptFragment = ScriptFragment.of(edge);
                    edge.setValue(scriptHandler.runScript(scriptFragment));
                }
            }
        });
    }
}