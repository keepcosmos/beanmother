package io.beanmother.core.mapper;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.fixture.FixtureList;
import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.fixture.FixtureTemplate;
import io.beanmother.core.fixture.FixtureValue;
import io.beanmother.core.mapper.converter.Converter;
import io.beanmother.core.mapper.converter.ConverterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Mapping property value by setter. Also support add prefix method if exists for array
 */
public class SetterMapper extends AbstractPropertyMapper {
    private static Logger logger = LoggerFactory.getLogger(SetterMapper.class);

    private static String SETTER_PREFIX = "set";
    private static String ADD_PREFIX = "add";

    public SetterMapper(ConverterFactory converterFactory) {
        super(converterFactory);
    }

    @Override
    public void map(Object target, String key, FixtureTemplate value) {
        if (value instanceof FixtureMap) {
            map(target, key, (FixtureValue) value);
        } else if (value instanceof FixtureList) {
            map(target, key, (FixtureList) value);
        } else if (value instanceof FixtureValue) {
            map(target, key, (FixtureValue) value);
        }
    }

    private void map(Object target, String key, FixtureMap value) {
        /**
         *  Parameter is size is value size
         *  Parameter is Map
         *      key is String
         *      key is Object
         *  Paramter is Object
         */
    }

    private void map(Object target, String key, FixtureValue value) {
        if (value == null || value.isNull()) return;
        List<Method> candidates = findSetterCandidates(target, key);

        for (Method candidate : candidates) {
            Object[] parameters = convertToMethodParameters(candidate, value);
            if (parameters == null || parameters.length != 1) continue;

            try {
                candidate.invoke(target, parameters);
            } catch (Exception e) {
                throw new FixtureMappingException(e);
            }

        }
    }

    private void map(Object target, String key, FixtureList value) {
        
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


    private Object[] convertToMethodParameters(Method method, FixtureValue value) {
        Class<?>[] paramTypes = method.getParameterTypes();
        if (paramTypes.length != 1) return null;

        Class targetType = paramTypes[0];

        if (targetType.isPrimitive()) {
            targetType = getPrimitiveWrapper(targetType);
        }

        TypeToken<?> targetTypeToken = TypeToken.of(targetType);
        Object source = value.getValue();

        Converter converter = getConverterFactory().get(source, targetTypeToken);

        if (converter != null) {
            return new Object[] { converter.convert(source, targetTypeToken) };
        }

        return null;
    }
}
