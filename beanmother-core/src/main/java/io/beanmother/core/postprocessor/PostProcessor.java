package io.beanmother.core.postprocessor;

import io.beanmother.core.common.FixtureMap;

import java.lang.reflect.ParameterizedType;

/**
 * Abstract class for post processing after a bean mapping.
 * @param <T> Target bean type
 */
public abstract class PostProcessor<T> implements Comparable<PostProcessor<T>> {

    /**
     * Default priority.
     */
    public static final int DEFAULT_PRIORITY = 5;

    private int priority = DEFAULT_PRIORITY;

    @SuppressWarnings("unchecked")
    private Class<T> targetClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    /**
     * Create a PostProcessor.
     */
    public PostProcessor() { }

    /**
     * Create a PostProcessor with a priority.
     * @param priority the priority
     */
    public PostProcessor(int priority) {
        this.priority = priority;
    }

    /**
     * Process
     * @param bean the mapped bean.
     * @param fixtureMap the source fixtureMap
     */
    public abstract void process(T bean, FixtureMap fixtureMap);

    /**
     * Get priority
     * @return The priority.
     */
    public int getPriority() {
        return this.priority;
    }

    public Class<T> getTargetClass() {
        return this.targetClass;
    }

    @Override
    public int compareTo(PostProcessor o) {
        return Integer.compare(getPriority(), o.getPriority());
    }
}
