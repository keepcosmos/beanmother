package io.beanmother.core.mapper.setter;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.Parameter;
import com.google.common.reflect.TypeToken;
import io.beanmother.core.fixture.FixtureValue;
import io.beanmother.core.mapper.FixtureMappingException;

import java.lang.reflect.Method;
import java.util.List;

public class FixtureValueSetterMapper extends AbstractFixtureSetterMapper<FixtureValue> {

    public FixtureValueSetterMapper(SetterMapperMediator setterMapperMediator) {
        super(setterMapperMediator);
    }

    @Override
    public void map(Object target, String key, FixtureValue value) {
        if (value == null || value.isNull()) return;
        List<Method> candidates = findSetterCandidates(target, key);

        for (Method candidate : candidates) {
            ImmutableList<Parameter> paramTypes = Invokable.from(candidate).getParameters();
            if (paramTypes == null || paramTypes.size() != 1) continue;
            TypeToken<?> paramType = paramTypes.get(0).getType();
            Object param = convert(value, paramType);
            if (param == null) continue;

            try {
                candidate.invoke(target, param);
            } catch (Exception e) {
                throw new FixtureMappingException(e);
            }
        }
    }
}
