package io.beanmother.core.script;

/**
 * The exception for throwing when script operation is failed.
 */
public class ScriptOperationException extends RuntimeException {

    public ScriptOperationException() {
    }

    public ScriptOperationException(String message) {
        super(message);
    }

    public ScriptOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScriptOperationException(Throwable cause) {
        super(cause);
    }

    public ScriptOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
