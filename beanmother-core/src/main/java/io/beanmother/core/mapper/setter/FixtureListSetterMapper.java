package io.beanmother.core.mapper.setter;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.Parameter;
import com.google.common.reflect.TypeToken;
import io.beanmother.core.fixture.FixtureList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class FixtureListSetterMapper extends AbstractFixtureSetterMapper<FixtureList> {
    private static Logger logger = LoggerFactory.getLogger(FixtureListSetterMapper.class);

    public FixtureListSetterMapper(SetterMapperMediator setterMapperMediator) {
        super(setterMapperMediator);
    }

    @Override
    public void map(Object target, String key, FixtureList fixtureList) {
        List<Method> candidates = findSetterCandidates(target, key);
        for (Method candidate :candidates) {
            try {
                ImmutableList<Parameter> paramTypes = Invokable.from(candidate).getParameters();
                if (paramTypes.size() != 1) continue;

                TypeToken<?> paramType = paramTypes.get(0).getType();
                Object candidateParam = convert(fixtureList, paramType);

                if (candidateParam != null) {
                    candidate.invoke(target, candidateParam);
                    return;
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
