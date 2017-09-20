package io.beanmother.core;

import io.beanmother.core.postprocessor.PostProcessor;

import java.util.List;

public interface BeanMother {
    <T> T bear(String fixtureName, T target);
    <T> T bear(String fixtureName, Class<T> targetClass);
    <T> T bear(String fixtureName, T target, PostProcessor<T> postProcessor);
    <T> T bear(String fixtureName, Class<T> targetClass, PostProcessor<T> postProcessor);
    <T> List<T> bear(String fixtureName, Class<T> targetClass, int size);
    <T> List<T> bear(String fixtureName, Class<T> targetClass, int size, PostProcessor<T> postProcessor);

    BeanMother addFixtureLocation(String path);
}
