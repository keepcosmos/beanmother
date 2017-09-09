package io.beanmother.core;

import io.beanmother.core.converter.ConverterFactory;
import io.beanmother.core.converter.StandardConverterFactory;
import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.fixture.FixtureMapTraversal;
import io.beanmother.core.fixture.FixtureValue;
import io.beanmother.core.fixture.store.DefaultFixturesStore;
import io.beanmother.core.fixture.store.FixturesStore;
import io.beanmother.core.mapper.DefaultFixtureMapper;
import io.beanmother.core.mapper.FixtureMapper;
import io.beanmother.core.script.DefaultScriptHandler;
import io.beanmother.core.script.ScriptFragment;
import io.beanmother.core.script.ScriptRunner;
import io.beanmother.core.util.Location;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBeanMother implements BeanMother {

    private FixturesStore fixturesStore = new DefaultFixturesStore();

    private ConverterFactory converterFactory = new StandardConverterFactory();

    private FixtureMapper fixtureMapper = new DefaultFixtureMapper(converterFactory);

    private ScriptRunner scriptRunner = new DefaultScriptHandler();

    @Override
    public <T> T bear(String fixtureName, T target) {
        FixtureMap fixtureMap = fixturesStore.reproduce(fixtureName);
        if (fixtureMap == null) {
            throw new IllegalArgumentException("can not find " + fixtureName);
        }

        FixtureMapTraversal.traverse(fixtureMap, new FixtureMapTraversal.Processor() {
            @Override
            public void visit(FixtureValue edge) {
                if (ScriptFragment.isScript(edge)) {
                    scriptRunner.runScript(edge);
                }
            }
        });

        fixtureMapper.map(fixtureMap, target);

        /**
         * run Prostprocessor
         */

        return target;
    }

    @Override
    public <T> T bear(String fixtureName, Class<T> targetClass) {
        T inst;
        try {
            inst = targetClass.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("can not instantiate " + targetClass);
        }
        return bear(fixtureName, inst);
    }

    @Override
    public <T> List<T> bear(String fixtureName, T target, int size) {
        List<T> result = new ArrayList<>();
        for (int i = 0 ; i < size ; i++) result.add(bear(fixtureName, target));
        return result;
    }

    @Override
    public <T> List<T> bear(String fixtureName, Class<T> targetClass, int size) {
        List<T> result = new ArrayList<>();
        for (int i = 0 ; i < size ; i++) result.add(bear(fixtureName, targetClass));
        return result;
    }

    @Override
    public BeanMother addFixtureLocation(String path) {
        fixturesStore.addLocation(new Location(path));
        return this;
    }
}