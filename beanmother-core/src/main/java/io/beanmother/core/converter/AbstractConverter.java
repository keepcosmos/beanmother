package io.beanmother.core.converter;

/**
 * Abstract converter
 */
public abstract class AbstractConverter implements Converter {

    private int priority;

    public AbstractConverter() {
        this(DEFAULT_PRIORITY);
    }

    public AbstractConverter(int priority) {
        this.priority = priority;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Converter o) {
        return Integer.compare(getPriority(), o.getPriority());
    }
}
