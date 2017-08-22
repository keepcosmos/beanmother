package io.beanmother.core.fixture;

import java.util.*;

/**
 * FixtureObject is decorator for the value of the {@link FixtureMap}
 *
 * The value of FixtureObject should be {@link java.util.List} or {@link java.util.Map} or plain Object such as String, Date, etc.
 * Generally, It parsed by {@link io.beanmother.core.fixture.parser.FixtureParser}
 *
 */
public class FixtureObject {

    private Object value;

    public FixtureObject(Object value) {
        this.value = value;
    }

    public boolean isList() {
        return value instanceof List;
    }

    public boolean isMap() {
        return value instanceof Map;
    }

    public Class getValueClass() {
        return this.value.getClass();
    }

    public Object getValue() {
        return this.value;
    }
}
