package io.beanmother.core.mapper;

import io.beanmother.core.common.FixtureMap;

public class ConstructHelper {
    private final static String CONSTRUCT_KEY = "_construct";

    public static Object construct(Class<?> type, FixtureMap fixtureMap) {
        if (fixtureMap.containsKey(CONSTRUCT_KEY)) {
            /**
             * TODO: Write
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
