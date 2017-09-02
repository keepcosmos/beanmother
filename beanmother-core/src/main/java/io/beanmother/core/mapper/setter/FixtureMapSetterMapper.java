package io.beanmother.core.mapper.setter;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.Parameter;
import com.google.common.reflect.TypeToken;
import io.beanmother.core.fixture.FixtureMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class FixtureMapSetterMapper extends AbstractFixtureSetterMapper<FixtureMap> {
    private static Logger logger = LoggerFactory.getLogger(FixtureMapSetterMapper.class);

    public FixtureMapSetterMapper(SetterMapperMediator setterMapperMediator) {
        super(setterMapperMediator);
    }

    @Override
    public void map(Object target, String key, FixtureMap fixtureMap) {
        List<Method> candidates = findSetterCandidates(target, key);
        for (Method candidate : candidates) {
            ImmutableList<Parameter> paramTypes = Invokable.from(candidate).getParameters();
            if (paramTypes.size() != 1) continue;

            TypeToken<?> paramType = paramTypes.get(0).getType();
            try {
                Object candidateParam = convert(fixtureMap, paramType);
                if (candidateParam != null) {
                    candidate.invoke(target, candidateParam);
                    return ;
                } else {
                    continue;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }
}
