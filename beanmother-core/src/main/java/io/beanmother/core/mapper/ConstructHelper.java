package io.beanmother.core.mapper;

import io.beanmother.core.common.FixtureMap;

/**
 * A ConstructHelper helps to create instance by values of a FixtureMap
 */
public abstract class ConstructHelper {

    /**
     * A key of FixtureMap that is a kind of source for creating a instance.
     */
    private final static String CONSTRUCT_KEY = "_construct";

    /**
     * Create instance of a given type.
     *
     * @param type the type
     * @param fixtureMap the fixtureMap
     * @return a instance of the type
     */
    public static Object construct(Class<?> type, FixtureMap fixtureMap) {
        if (fixtureMap.containsKey(CONSTRUCT_KEY)) {
            /**
             * TODO: create instance by fixtureMap's "_constructor" key
             */
            throw new UnsupportedOperationException("can not create a instance of " + type + " by '" + fixtureMap.getFixtureName() + "'");
        } else {
            try {
                return type.newInstance();
            } catch (Exception e) {
                throw new FixtureMappingException("can not create a instance of " + type + " by '" + fixtureMap.getFixtureName() + "'", e);
            }
        }
    }
}
