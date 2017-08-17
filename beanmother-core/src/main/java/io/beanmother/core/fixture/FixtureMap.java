package io.beanmother.core.fixture;

import io.beanmother.core.fixture.parser.FixtureFormatException;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * FixtureMap Structure.
 * It store a fixture data with name.
 *
 * Generally, fixtureMap is a raw Map data just read from a fixture file.
 */
public class FixtureMap extends LinkedHashMap {

    /**
     * The name of fixture.
     */
    private String fixtureName;

    /**
     * For lazy thrown FixtureFormatException when it call data.
     */
    private FixtureFormatException fixtureFormatException;

    /**
     * check for cloned
     */
    private boolean cloned;

    public FixtureMap(String fixtureName) {
        super();
        this.fixtureName = fixtureName;
    }

    public FixtureMap(String fixtureName, Map data) {
        super(data);
        this.fixtureName = fixtureName;
    }

    public FixtureMap(String fixtureName, FixtureFormatException e) {
        super();
        this.fixtureName = fixtureName;
        this.fixtureFormatException = e;
    }

    /**
     * Get fixture name.
     */
    public String getFixtureName() {
        return fixtureName;
    }

    /**
     * Get fixture format exception.
     * @return
     */
    public FixtureFormatException getFixtureFormatException() {
        return fixtureFormatException;
    }

    /**
     * Check stored fixture data is valid or not.
     * @return true if valid.
     */
    public boolean isInvalidFormat() {
        return fixtureFormatException != null;
    }

    /**
     * @return true if this is cloned fixtureMap
     */
    public boolean isCloned() {
        return cloned;
    }

    /**
     * Reproduce this.
     *
     * Generally, data is changed by pre-processors before mapping to bean object.
     * Use this to prevent unexpected change of origin fixtureMap.
     *
     * @return reproduced data
     */
    public FixtureMap reproduce() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024 * 2);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            oos.flush();
            oos.close();

            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            Object obj = ois.readObject();
            return (FixtureMap) obj;
        } catch (Exception e) {
            throw new FixtureFormatException(getFixtureName(), e);
        }
    }
}
