package io.beanmother.core.converter;

/**
 * The exception for throwing when object converting is failed.
 */
public class ConverterException extends RuntimeException {

    /**
     * Create an ConverterException with a specific message.
     * @param message the message
     */
    public ConverterException(String message) {
        super(message);
    }

    /**
     * Create an ConverterException with a specific message and a cause.
     * @param message the message
     * @param cause the cause
     */
    public ConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create an ConverterException with a cause.
     * @param cause the cause
     */
    public ConverterException(Throwable cause) {
        super(cause);
    }

    /**
     * Create an ConverterException with a source, a target class and a specific message.
     * @param source the source
     * @param targetClass the target class
     * @param message the message
     */
    public ConverterException(Object source, Class<?> targetClass, String message) {
        this("can not convert " + source + " to " + targetClass + ", " + message);
    }

    /**
     * Create an ConverterException with a source, a target class, a specific message and a cause.
     * @param source the source
     * @param targetClass the target class
     * @param message the message
     * @param cause the cause
     */
    public ConverterException(Object source, Class<?> targetClass, String message, Throwable cause) {
        this("can not convert " + source + " to " + targetClass, cause);
    }
}
