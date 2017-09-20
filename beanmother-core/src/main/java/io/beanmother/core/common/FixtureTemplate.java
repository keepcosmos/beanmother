package io.beanmother.core.common;

import java.io.Serializable;

/**
 * The root interface for {@link FixtureValue}, {@link FixtureList} and {@link FixtureMap}
 */
public interface FixtureTemplate extends Serializable {

    /**
     * Check if a FixtureTemplate is a root
     */
    boolean isRoot();

    /**
     * Set true if a FixtureTemplate is a root.
     */
    void setRoot(boolean root);

    /**
     * Get a name of a FixtureTemplate.
     */
    String getFixtureName();

    /**
     * Set a name of a FixtureTemplate.
     */
    void setFixtureName(String fixtureName);

    /**
     * Get a parent of a FixtureTemplate.
     */
    FixtureTemplate getParent();

    /**
     * Set a parent of a FixtureTemplate.
     */
    void setParent(FixtureTemplate parent);

    /**
     * Check If a FixtureTemplate has a parent.
     */
    boolean hasParent();

}
