package io.beanmother.core.fixture;

/**
 * The root interface for {@link FixtureValue}, {@link FixtureList} and {@link FixtureMap}
 */
public interface FixtureTemplate {

    boolean isRoot();

    void setRoot(boolean root);

    String getFixtureName();

    void setFixtureName(String fixtureName);

    FixtureTemplate getParent();

    void setParent(FixtureTemplate parent);

    boolean hasParent();

}
