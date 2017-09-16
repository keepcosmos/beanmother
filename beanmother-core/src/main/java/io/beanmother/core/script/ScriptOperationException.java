package io.beanmother.core.script;

/**
 * The exception for throwing when script operation is failed.
 */
public class ScriptOperationException extends RuntimeException {

    /**
     * Create an ScriptOperationException with a specific message and a cause.
     * @param message the message
     */
    public ScriptOperationException(String message) {
        super(message);
    }

    /**
     * Create an ScriptOperationException with a specific message and a cause.
     * @param message the message
     * @param cause the cause
     */
    public ScriptOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
