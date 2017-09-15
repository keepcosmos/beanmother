package io.beanmother.core.mapper;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.Parameter;
import com.google.common.reflect.TypeToken;
import io.beanmother.core.common.FixtureList;
import io.beanmother.core.common.FixtureMap;
import io.beanmother.core.common.FixtureValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * SetterFixtureMapper is a implementation of {@link FixtureMapper}. It maps target object properties by setter.
 */
public class SetterFixtureMapper extends AbstractFixtureMapper implements FixtureMapper {
    private final static Logger logger = LoggerFactory.getLogger(SetterFixtureMapper.class);

    /**
     * A prefix of setter names
     */
    private final static String SETTER_PREFIX = "set";

    /**
     * Create a SetterFixtureMapper
     *
     * @param mapperMediator
     */
    public SetterFixtureMapper(MapperMediator mapperMediator) {
        super(mapperMediator);
    }

    @Override
    protected void bind(Object target, String key, FixtureMap fixtureMap) {
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
                }
            } catch (Exception e) {
                throw new FixtureMappingException(e);
            }
        }
    }

    @Override
    protected void bind(Object target, String key, FixtureList fixtureList) {
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
                }
            } catch (Exception e) {
                throw new FixtureMappingException(e);
            }
        }
    }

    @Override
    protected void bind(Object target, String key, FixtureValue fixtureValue) {
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
