package io.beanmother.core.common;

import java.io.Serializable;

/**
 * Metadata for {@link FixtureTemplate}
 */
public class FixtureMetadata implements Serializable {

    private FixtureTemplate owner;

    private FixtureTemplate parent;

    private String fixtureName;

    private boolean root;

    /**
     * Create a FixtureMetadata with a specific FixtureTemplate
     */
    public FixtureMetadata(FixtureTemplate owner) {
        this.owner = owner;
    }

    /**
     * Check FixtureTemplate is a root.
     */
    public boolean isRoot() {
        return root;
    }

    /**
     * Get a name of FixtureTemplate.
     */
    public String getFixtureName() {
        return fixtureName;
    }

    /**
     * Get a parent of FixtureTemplate.
     */
    public FixtureTemplate getParent() {
        return parent;
    }

    /**
     * Check FixtureTemplate has a parent.
     */
    public boolean hasParent() {
        return getParent() != null;
    }

    /**
     * Set a parent of FixtureTemplate.
     */
    public void setParent(FixtureTemplate parent) {
        this.parent = parent;
    }

    /**
     * Set a name of FixtureTemplate.
     */
    public void setFixtureName(String fixtureName) {
        this.fixtureName = fixtureName;
    }

    /**
     * Set true if FixtureTemplate is a root
     */
    public void setRoot(boolean root) {
        this.root = root;
    }
}
