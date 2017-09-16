package io.beanmother.core.mapper;

/**
 * A FixtureMappingExcpetion is thrown by an FixtureMapper if it has an unexpected problem.
 */
public class FixtureMappingException extends RuntimeException {

    /**
     * Create an FixtureMappingException with a specific message.
     * @param message the message
     */
    public FixtureMappingException(String message) {
        super(message);
    }

    /**
     * Create an FixtureMappingException with a specific message and cause.
     *
     * @param message the message
     * @param cause the cause
     */
    public FixtureMappingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create an FixtureMappingException with a cause.
     *
     * @param cause the cause
     */
    public FixtureMappingException(Throwable cause) {
        super(cause);
    }
}
