package io.beanmother.core.common;

import java.util.ArrayList;

/**
 * FixtureList decorates a list from fixture
 *
 * The source of FixtureList generally {@link ArrayList<Object>} that is parsed by {@link io.beanmother.core.loader.parser.FixtureParser}
 */
public class FixtureList extends ArrayList<FixtureTemplate> implements FixtureTemplate {

    private FixtureMetadata metadata;

    /**
     * Create FixtureList
     */
    public FixtureList() {
        super();
        this.metadata = new FixtureMetadata(this);
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
}
