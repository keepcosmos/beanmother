package io.beanmother.core;

import java.util.List;

public interface BeanMother {
    <T> T bear(String fixtureName, T target);
    <T> T bear(String fixtureName, Class<T> targetClass);
    <T> List<T> bear(String fixtureName, T target, int size);
    <T> List<T> bear(String fixtureName, Class<T> targetClass, int size);

    BeanMother addFixtureLocation(String path);
}
