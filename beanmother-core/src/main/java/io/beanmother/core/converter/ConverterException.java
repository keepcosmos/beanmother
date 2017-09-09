package io.beanmother.core.converter;

/**
 * The exception for throwing when object converting is failed.
 */
public class ConverterException extends RuntimeException {

    public ConverterException(String message) {
        super(message);
    }

    public ConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConverterException(Throwable cause) {
        super(cause);
    }

    public ConverterException(Object source, Class<?> targetClass, String message) {
        this("can not convert " + source + " to " + targetClass + ", " + message);
    }

    public ConverterException(Object source, Class<?> targetClass, String message, Throwable cause) {
        this("can not convert " + source + " to " + targetClass, cause);
    }
}
