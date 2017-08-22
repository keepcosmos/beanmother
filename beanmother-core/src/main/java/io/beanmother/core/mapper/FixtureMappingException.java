package io.beanmother.core.mapper;

public class FixtureMappingException extends RuntimeException {
    public FixtureMappingException() {
    }

    public FixtureMappingException(String message) {
        super(message);
    }

    public FixtureMappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FixtureMappingException(Throwable cause) {
        super(cause);
    }

    public FixtureMappingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
