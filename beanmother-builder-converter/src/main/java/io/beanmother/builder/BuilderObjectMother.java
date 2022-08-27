package io.beanmother.builder;

import java.lang.reflect.InvocationTargetException;

import io.beanmother.core.AbstractBeanMother;
import io.beanmother.core.common.FixtureMap;
import io.beanmother.core.common.FixtureTemplate;
import io.beanmother.core.common.FixtureValue;
import io.beanmother.core.converter.ConverterFactory;
import io.beanmother.core.mapper.ConstructHelper;
import io.beanmother.core.mapper.DefaultFixtureMapper;
import io.beanmother.core.postprocessor.PostProcessor;

public class BuilderObjectMother extends AbstractBeanMother {

    private final static BuilderObjectMother beanMother = new BuilderObjectMother();

    public static BuilderObjectMother getInstance() {
        return beanMother;
    }
	
    /**
     * A key of FixtureMap that is a kind of source for creating a instance, using builder pattern.
     */
    public final static String INIT_BUILDER_KEY = "_initBuilder";
    public final static String FINISH_BUILDER_KEY = "_finishBuilder";
	public final static String TARGET_BUILDER_KEY = "_targetClass";
	public final static String CONSTRUCT_BUILDER_KEY = "_construct";
	public final static String SETTER_PREFIX_KEY = "_setterPrefix";

    public BuilderObjectMother() {
    	super();
	}

    private static Object executeByFixture(Class<?> type, FixtureValue fixtureValue) {
        try {
			return type.getMethod((String)fixtureValue.getValue(), null).invoke(type, null);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    @Override
    public <T> T bear(String fixtureName, Class<T> targetClass) {
    	PostProcessor<T> pp = null;
    	return bear(fixtureName,targetClass, pp);
    }
    
    @Override
    public <T> T bear(String fixtureName, Class<T> targetClass, PostProcessor<T> postProcessor) {
        FixtureMap fixtureMap = getFixturesStore().reproduce(fixtureName);
        
        T inst = null;

        if (fixtureMap.containsKey(INIT_BUILDER_KEY)) {
	    	FixtureTemplate constructorFixture = fixtureMap.get(INIT_BUILDER_KEY);
	    	if (constructorFixture instanceof FixtureValue) {
	    		inst = (T) executeByFixture(targetClass, (FixtureValue) constructorFixture);
	        }
	    } else if (fixtureMap.containsKey(CONSTRUCT_BUILDER_KEY)) {
			// Use the target class by fixture, not by method call
			FixtureValue targetFixtureAux = (FixtureValue) fixtureMap.get(TARGET_BUILDER_KEY);
			try {
				inst = (T) ConstructHelper.construct(Class.forName(targetFixtureAux.getValue().toString()), fixtureMap, null);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		if (fixtureMap.containsKey(SETTER_PREFIX_KEY)) {
			FixtureValue customSetter = (FixtureValue) fixtureMap.get(SETTER_PREFIX_KEY);
        	this.setSetterPrefix((String) customSetter.getValue());
		}
		
		if (inst!=null) {
			_bear(inst, fixtureMap, postProcessor);

			if (fixtureMap.containsKey(FINISH_BUILDER_KEY)) {
				FixtureTemplate finishFixture = fixtureMap.get(FINISH_BUILDER_KEY);
				if (finishFixture instanceof FixtureValue) {
	
					FixtureTemplate targetFixture = fixtureMap.get(TARGET_BUILDER_KEY);
					
					try {
						// Create a new JavaClassLoader
						ClassLoader classLoader = this.getClass().getClassLoader();

						if (targetFixture!=null) {
							// Load the target class using its binary name
							Class loadedMyClass = classLoader.loadClass(((FixtureValue)targetFixture).getValue().toString());
	
							inst = (T) loadedMyClass.getMethod((String)((FixtureValue)finishFixture).getValue(), null).invoke(inst, null);
						} else {
							// targetClass not found
							inst = null;
						}
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
							| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
						
						// finish not found
						inst = null;

						e.printStackTrace();
					}
				}
			}    	
		}
        
        return inst;

    }

}
