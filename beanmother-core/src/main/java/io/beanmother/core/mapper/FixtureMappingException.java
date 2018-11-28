package io.beanmother.core.mapper;

import io.beanmother.core.common.FixtureMap;

/**
 * A FixtureMappingExcpetion is thrown by an FixtureMapper if it has an unexpected problem.
 */
public class FixtureMappingException extends RuntimeException {

    /**
     * Create a FixtureMappingException with a specific message.
     * @param message the message
     */
    public FixtureMappingException(String message) {
        super(message);
    }

    /**
     * Create a FixtureMappingException with a type, a fixtureMap and a cause
     * @param type the type
     * @param fixtureMap the fixtureMap
     * @param cause the cause
     */
    public FixtureMappingException(Class<?> type, FixtureMap fixtureMap, Throwable cause) {
        super("can not create an instance of " + type + " by fixture name '" + fixtureMap.getFixtureName() + "'", cause);
    }

    /**
     * Create a FixtureMappingException with a specific message and cause.
     *
     * @param message the message
     * @param cause the cause
     */
    public FixtureMappingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a FixtureMappingException with a cause.
     *
     * @param cause the cause
     */
    public FixtureMappingException(Throwable cause) {
        super(cause);
    }
}
