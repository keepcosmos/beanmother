package io.beanmother.core.mapper;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.Parameter;
import com.google.common.reflect.TypeToken;
import io.beanmother.core.common.FixtureList;
import io.beanmother.core.common.FixtureMap;
import io.beanmother.core.common.FixtureTemplate;
import io.beanmother.core.common.FixtureValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * SetterAndFieldFixtureMapper is a implementation of {@link FixtureMapper}.
 *
 * It maps target object properties by setter and maps public field as a fallback.
 */
public class SetterAndFieldFixtureMapper extends AbstractFixtureMapper implements FixtureMapper {
    private final static Logger logger = LoggerFactory.getLogger(SetterAndFieldFixtureMapper.class);

    /**
     * A prefix of setter names
     */
    private final static String SETTER_PREFIX = "set";

    /**
     * Create a SetterAndFieldFixtureMapper
     *
     * @param mapperMediator
     */
    public SetterAndFieldFixtureMapper(MapperMediator mapperMediator) {
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

        bindByField(target, key, fixtureMap);
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

        bindByField(target, key, fixtureList);
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

        bindByField(target, key, fixtureValue);
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

    private void bindByField(Object target, String key, FixtureTemplate template) {
        Field field = findField(target.getClass(), key);
        if (field == null) {
            //Lets try private fields as well
            try {
                field = target.getClass().getDeclaredField(key);
                field.setAccessible(true);//Very important, this allows the setting to work.
            } catch (NoSuchFieldException e) {
                return;
            }
        }

        TypeToken<?> targetType = TypeToken.of(field.getGenericType());
        Object value = getFixtureConverter().convert(template, targetType);
        if (value == null) return;

        try {
            field.set(target, value);
        } catch (IllegalAccessException e) {
            throw new FixtureMappingException(e);
        }
    }

    private Field findField(Class<?> targetClass, String key) {
        try {
            return targetClass.getField(key);
        } catch (NoSuchFieldException e) {
            Class<?> superClass = targetClass.getSuperclass();
            if (superClass == null || superClass == Object.class) {
                return null;
            }
            return findField(targetClass.getSuperclass(), key);
        }
    }
}
