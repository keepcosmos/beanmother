package io.beanmother.core.loader.parser;

/**
 * Exception thrown when a {@link FixtureParser} encounter an error when
 * attempting to parse fixture raw string to Map instance.
 */
public class FixtureFormatException extends RuntimeException {

    /**
     * Create a new FixtureFormatException.
     */
    public FixtureFormatException(String fixtureName, String msg) {
        super("Error creating fixture with name '" + fixtureName + "' " + msg);
    }

    /**
     * Create a new FixtureFormatException.
     */
    public FixtureFormatException(String fixtureName) {
        this(fixtureName, "");
    }

    /**
     * Create a new FixtureFormatException by throwable.
     */
    public FixtureFormatException(String fixtureName, Throwable throwable) {
        super("Error creating fixture with name '" + fixtureName + "'", throwable);
    }

    /**
     * Create a new FixtureFormatException by throwable.
     */
    public FixtureFormatException(Throwable throwable) {
        super(throwable);
    }

}
