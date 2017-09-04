package io.beanmother.core.mapper;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.Parameter;
import com.google.common.reflect.TypeToken;
import io.beanmother.core.fixture.FixtureList;
import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.fixture.FixtureTemplate;
import io.beanmother.core.fixture.FixtureValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * FixtureSetterMapper is a implementation of {@link FixtureMapper}. It maps target object properties by setter.
 */
public class FixtureSetterMapper implements FixtureMapper {
    private final static Logger logger = LoggerFactory.getLogger(FixtureSetterMapper.class);

    /**
     * A prefix of setter names
     */
    private final static String SETTER_PREFIX = "set";

    private MapperMediator mapperMediator;

    /**
     * Create a FixtureSetterMapper
     * @param mapperMediator
     */
    public FixtureSetterMapper(MapperMediator mapperMediator) {
        this.mapperMediator = mapperMediator;
    }

    @Override
    public <T> T map(FixtureMap fixtureMap, Class<T> targetType) {
        try {
            T target = targetType.newInstance();
            map(fixtureMap, target);
            return target;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void map(FixtureMap fixtureMap, Object target) {
        for (Map.Entry<String, FixtureTemplate> entry : fixtureMap.entrySet()) {
            map(target, entry.getKey(), entry.getValue());
        }
    }

    /**
     * Set a value of the key to target object.
     * @param target
     * @param key
     * @param fixtureTemplate
     */
    public void map(Object target, String key, FixtureTemplate fixtureTemplate) {
        if (fixtureTemplate instanceof FixtureMap) {
            map(target, key, (FixtureMap) fixtureTemplate);
        } else if (fixtureTemplate instanceof FixtureList) {
            map(target, key, (FixtureList) fixtureTemplate);
        } else if (fixtureTemplate instanceof FixtureValue) {
            map(target, key, (FixtureValue) fixtureTemplate);
        }
    }

    public FixtureConverter getFixtureConverter() {
        return mapperMediator.getFixtureConverter();
    }

    protected void map(Object target, String key, FixtureMap fixtureMap) {
        List<Method> candidates = findSetterCandidates(target, key);
        for (Method candidate : candidates) {
            ImmutableList<Parameter> paramTypes = Invokable.from(candidate).getParameters();
            if (paramTypes.size() != 1) continue;

            TypeToken<?> paramType = paramTypes.get(0).getType();
            try {
                Object candidateParam = getFixtureConverter().convert(fixtureMap, paramType);
                if (candidateParam != null) {
                    candidate.invoke(target, candidateParam);
                    return;
                } else {
                    continue;
                }
            } catch (Exception e) {
                throw new FixtureMappingException(e);
            }
        }
    }

    protected void map(Object target, String key, FixtureList fixtureList) {
        List<Method> candidates = findSetterCandidates(target, key);
        for (Method candidate :candidates) {
            try {
                ImmutableList<Parameter> paramTypes = Invokable.from(candidate).getParameters();
                if (paramTypes.size() != 1) continue;

                TypeToken<?> paramType = paramTypes.get(0).getType();
                Object candidateParam = getFixtureConverter().convert(fixtureList, paramType);

                if (candidateParam != null) {
                    candidate.invoke(target, candidateParam);
                    return;
                } else {
                    continue;
                }
            } catch (Exception e) {
                throw new FixtureMappingException(e);
            }
        }
    }

    protected void map(Object target, String key, FixtureValue fixtureValue) {
        if (fixtureValue == null || fixtureValue.isNull()) return;
        List<Method> candidates = findSetterCandidates(target, key);

        for (Method candidate : candidates) {
            ImmutableList<Parameter> paramTypes = Invokable.from(candidate).getParameters();
            if (paramTypes == null || paramTypes.size() != 1) continue;
            TypeToken<?> paramType = paramTypes.get(0).getType();
            Object param = getFixtureConverter().convert(fixtureValue, paramType);
            if (param == null) continue;

            try {
                candidate.invoke(target, param);
            } catch (Exception e) {
                throw new FixtureMappingException(e);
            }
        }
    }

    private List<Method> findSetterCandidates(Object target, String key) {
        Method[] methods = target.getClass().getMethods();
        List<Method> result = new ArrayList<>();
        for (Method method : methods) {
            String name = method.getName();
            if(name.indexOf(SETTER_PREFIX) == 0) {
                if (name.substring(SETTER_PREFIX.length(), name.length()).equalsIgnoreCase(key)) {
                    result.add(method);
                }
            }
        }
        return result;
    }
}
