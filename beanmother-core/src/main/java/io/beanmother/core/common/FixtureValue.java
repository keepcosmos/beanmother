package io.beanmother.core.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FixtureValue decorates a value from a fixture
 *
 * The value of FixtureValue should plain object such as String, Date, Boolean, etc.
 * Generally, It parsed by {@link io.beanmother.core.loader.parser.FixtureParser}
 */
public class FixtureValue extends Object implements FixtureTemplate {

    /**
     * Origin value
     */
    private Object value;
    private FixtureMetadata metadata;

    /**
     * Create FixtureValue
     * @param value
     */
    public FixtureValue(Object value) {
        this.value = value;
        this.metadata = new FixtureMetadata(this);
    }

    /**
     * Get origin value
     * @return origin value
     */
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isNull() {
        return value == null;
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

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static void main(String[] args) {
        Pattern patter = Pattern.compile("(?<=\\$\\{)(.+?)(?=})");
        Matcher matcher = patter.matcher("abcd${abc}abc");
        System.out.println(matcher.find());
        System.out.println(matcher.group(0));
    }
}
