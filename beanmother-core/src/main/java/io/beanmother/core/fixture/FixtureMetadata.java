package io.beanmother.core.fixture;

public class FixtureMetadata {

    private FixtureTemplate owner;

    private FixtureTemplate parent;

    private String fixtureName;

    private boolean root;

    public FixtureMetadata(FixtureTemplate owner) {
        this.owner = owner;
    }

    public boolean isRoot() {
        return root;
    }

    public String getFixtureName() {
        return fixtureName;
    }

    public FixtureTemplate getParent() {
        return parent;
    }

    public boolean hasParent() {
        return getParent() != null;
    }

    public void setParent(FixtureTemplate parent) {
        this.parent = parent;
    }

    public void setFixtureName(String fixtureName) {
        this.fixtureName = fixtureName;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }
}
