package io.beanmother.core.fixture;

import io.beanmother.core.fixture.parser.FixtureFormatException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

/**
 * FixtureMap decorates a map from fixture
 *
 * The source of FixtureMap generally {@link LinkedHashMap<String, Object>} that is parsed by {@link io.beanmother.core.fixture.parser.FixtureParser}.
 * It can be the root fixture template.
 */
public class FixtureMap extends LinkedHashMap<String, FixtureTemplate> implements FixtureTemplate {

    private FixtureMetadata metadata;

    /**
     * Create FixtureMap
     */
    public FixtureMap() {
        super();
        this.metadata = new FixtureMetadata(this);
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
