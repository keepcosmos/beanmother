package io.beanmother.core.postprocessor;

import io.beanmother.core.common.FixtureMap;

public abstract class PostProcessor<T> implements Comparable<PostProcessor<T>> {

    private final static int DEFAULT_PRIORITY = 5;

    private int priority = DEFAULT_PRIORITY;

    public PostProcessor() { }

    public PostProcessor(int priority) {
        this.priority = priority;
    }

    public abstract void process(T bean, FixtureMap fixtureMap);

    public int getPriority() {
        return DEFAULT_PRIORITY;
    }

    @Override
    public int compareTo(PostProcessor o) {
        return Integer.compare(getPriority(), o.getPriority());
    }
}
