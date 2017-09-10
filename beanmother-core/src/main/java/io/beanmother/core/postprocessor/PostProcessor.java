package io.beanmother.core.postprocessor;

import io.beanmother.core.common.FixtureMap;

public abstract class PostProcessor<T> implements Comparable<PostProcessor<T>> {

    private final static int DEFAULT_PRIORITY = 5;

    public abstract void process(T bean, FixtureMap fixtureMap);

    public int getPriority() {
        return DEFAULT_PRIORITY;
    }

    @Override
    public int compareTo(PostProcessor o) {
        return Integer.compare(getPriority(), o.getPriority());
    }
}
