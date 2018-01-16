package io.beanmother.core.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.reflect.TypeToken;

import io.beanmother.core.common.FixtureList;
import io.beanmother.core.common.FixtureMap;
import io.beanmother.core.common.FixtureTemplate;
import io.beanmother.core.common.FixtureValue;

/**
 * A ConstructHelper helps to create instance by values of a FixtureMap
 */
public abstract class ConstructHelper {

    /**
     * A key of FixtureMap that is a kind of source for creating a instance.
     */
    private final static String CONSTRUCT_KEY = "_construct";

    /**
     * A key of FixtureMap that is a kind of source for creating a instance, using builder pattern.
     */
    private final static String BUILDER_KEY = "_builder";
    
    /**
     * Create instance of a given type.
     *
     * @param type the type
     * @param fixtureMap the fixtureMap
     * @return a instance of the type
     */
    @SuppressWarnings("unchecked")
    public static Object construct(Class<?> type, FixtureMap fixtureMap, FixtureConverter fixtureConverter) {
//        final Constructor<?>[] constructs = type.getConstructors();
//        if (constructs.length == 0) throw new UnsupportedOperationException("cna not create a instance. " + type + " has not constructor.");

        Object newInstance = null;

        if (fixtureMap.containsKey(CONSTRUCT_KEY)) {
            FixtureTemplate constructorFixture = fixtureMap.get(CONSTRUCT_KEY);

            if (constructorFixture instanceof FixtureValue) {
                newInstance = constructByFixtureValue(type, (FixtureValue) constructorFixture, fixtureConverter);
            } else if (constructorFixture instanceof FixtureList) {
                newInstance = constructByFixtureList(type, (FixtureList) constructorFixture, fixtureConverter);
            }
            
            if (newInstance == null) {
                try {
                    newInstance = type.newInstance();
                } catch (Exception e) {
                    throw new FixtureMappingException(type, fixtureMap, e);
                }
            }            
            
        } else if (fixtureMap.containsKey(BUILDER_KEY)) {
        	FixtureTemplate constructorFixture = fixtureMap.get(BUILDER_KEY);
        	if (constructorFixture instanceof FixtureValue) {
                newInstance = constructByFixtureCustom(type, (FixtureValue) constructorFixture, fixtureConverter);
            }
        }

        return newInstance;
    }

    private static Object constructByFixtureList(Class<?> type, FixtureList fixtureList, FixtureConverter fixtureConverter) {
        List<Constructor> candidates = new ArrayList<>();
        for (Constructor constructor : type.getConstructors()) {
            if (constructor.getParameterTypes().length == fixtureList.size()) {
                candidates.add(constructor);
            }
        }

        for (Constructor constructor : candidates) {
            List<Object> params = new ArrayList<>();
            Class<?>[] paramTypes = constructor.getParameterTypes();
            for (int i = 0; i < paramTypes.length ; i++) {
                Object param = fixtureConverter.convert(fixtureList.get(i), TypeToken.of(paramTypes[i]));
                if (param == null) {
                    break;
                } else {
                    params.add(param);
                }
            }

            if (params.size() == paramTypes.length) {
                try {
                    return constructor.newInstance(params.toArray());
                } catch (Exception e) {
                    // Do nothing. Let it run loop.
                }
            }
        }

        return null;
    }

    private static Object constructByFixtureCustom(Class<?> type, FixtureValue fixtureValue, FixtureConverter fixtureConverter) {
        try {
			return type.getMethod((String)fixtureValue.getValue(), null).invoke(type, null);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }    
    
    @SuppressWarnings("unchecked")
    private static Object constructByFixtureValue(Class<?> type, FixtureValue fixtureValue, FixtureConverter fixtureConverter) {
        for (Constructor constructor : type.getConstructors()) {
            if (constructor.getParameterTypes().length == 1) {
                Object param = fixtureConverter.convert(fixtureValue, TypeToken.of(constructor.getParameterTypes()[0]));
                if (param != null) {
                    try {
                        return constructor.newInstance(param);
                    } catch (Exception e) {
                        // Do thing. Let it run loop
                    }
                }
            }
        }
        return null;
    }
}
