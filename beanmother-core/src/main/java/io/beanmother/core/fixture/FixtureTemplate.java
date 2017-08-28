package io.beanmother.core.fixture;

import java.io.Serializable;

/**
 * The root interface for {@link FixtureValue}, {@link FixtureList} and {@link FixtureMap}
 */
public interface FixtureTemplate extends Serializable {

    boolean isRoot();

    void setRoot(boolean root);

    String getFixtureName();

    void setFixtureName(String fixtureName);

    FixtureTemplate getParent();

    void setParent(FixtureTemplate parent);

    boolean hasParent();

}
