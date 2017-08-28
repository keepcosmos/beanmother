package io.beanmother.core.fixture;

import com.google.common.reflect.TypeToken;

/**
 * FixtureValue decorates a value from a fixture
 *
 * The value of FixtureValue should plain object such as String, Date, Boolean, etc.
 * Generally, It parsed by {@link io.beanmother.core.fixture.parser.FixtureParser}
 */
public class FixtureValue extends Object implements FixtureTemplate {

    /**
     * Origin value
     */
    private Object value;
    private TypeToken typeToken;
    private FixtureMetadata metadata;

    /**
     * Create FixtureValue
     * @param value
     */
    public FixtureValue(Object value) {
        this.value = value;
        this.typeToken = TypeToken.of(value.getClass());
        this.metadata = new FixtureMetadata(this);
    }

    /**
     * Get origin value
     * @return origin value
     */
    public Object getValue() {
        return value;
    }

    public boolean isNull() {
        return value == null;
    }

    public TypeToken getTypeToken() {
        return typeToken;
    }

    @Override
    public boolean isRoot() {
        return metadata.isRoot();
    }

    @Override
    public void setRoot(boolean root) {
        metadata.setRoot(root);
    }

    @Override
    public String getFixtureName() {
        return metadata.getFixtureName();
    }

    @Override
    public void setFixtureName(String fixtureName) {
        metadata.setFixtureName(fixtureName);
    }

    @Override
    public FixtureTemplate getParent() {
        return metadata.getParent();
    }

    @Override
    public void setParent(FixtureTemplate parent) {
        metadata.setParent(parent);
    }

    @Override
    public boolean hasParent() {
        return metadata.hasParent();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof FixtureValue)) return false;
        return ((FixtureValue) obj).getValue().equals(this.getValue());
    }
}
