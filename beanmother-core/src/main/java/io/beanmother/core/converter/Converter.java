package io.beanmother.core.converter;

import com.google.common.reflect.TypeToken;

/**
 * Converters implement this interface.
 */
public interface Converter extends Comparable<Converter> {
    int DEFAULT_PRIORITY = 5;

    /**
     * Convert to given type
     *
     * @param source the source
     * @param targetTypeToken the TypeToken of the type
     * @return converted object
     */
    Object convert(Object source, TypeToken<?> targetTypeToken);

    /**
     * Check that source can convert to given type
     *
     * @param source the source
     * @param targetTypeToken the TypeToken of the type
     * @return true if it can convert
     */
    boolean canHandle(Object source, TypeToken<?> targetTypeToken);

    /**
     * Executing priority. Low value is high priority.
     * @return priority
     */
    int getPriority();
}
