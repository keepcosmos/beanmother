package io.beanmother.core.mapper.setter;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.Parameter;
import com.google.common.reflect.TypeToken;
import io.beanmother.core.fixture.FixtureList;
import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.fixture.FixtureTemplate;
import io.beanmother.core.fixture.FixtureValue;
import io.beanmother.core.mapper.AbstractPropertyMapper;
import io.beanmother.core.mapper.FixtureMappingException;
import io.beanmother.core.converter.Converter;
import io.beanmother.core.converter.ConverterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
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
        List<Method> candidates = findSetterCandidates(target, key);

        for (Method candidate :candidates) {
            Invokable invokable = Invokable.from(candidate);
            ImmutableList<Parameter> params = invokable.getParameters();
            if (params.size() != 1) continue;

            TypeToken<?> paramType = params.get(0).getType();

            if (paramType.isSubtypeOf(TypeToken.of(List.class))) {
                TypeToken genericTypeToken = null;
                try {
                    Class<?> genericType = (Class<?>) ((ParameterizedType) paramType.getType()).getActualTypeArguments()[0];
                    genericTypeToken = TypeToken.of(genericType);
                } catch (ClassCastException e) {
                    genericTypeToken = TypeToken.of(Object.class);
                }

                if (paramType.getRawType().isInterface()) {
                    List list = new ArrayList();

                    for(FixtureTemplate template : value) {
                        if (!(template instanceof FixtureValue)) continue;
                        FixtureValue fixtureValue = (FixtureValue) template;
                        Converter converter = getConverterFactory().get(fixtureValue.getValue(), genericTypeToken);
                        Object obj = converter.convert(fixtureValue.getValue(), genericTypeToken);
                        list.add(obj);
                    }
                    try {
                        candidate.invoke(target, list);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            } else if(paramType.isArray()) {
                TypeToken<?> componentType = paramType.getComponentType();
                List list = new ArrayList();

                for(FixtureTemplate template : value) {
                    if (!(template instanceof FixtureValue)) continue;
                    FixtureValue fixtureValue = (FixtureValue) template;
                    Converter converter = getConverterFactory().get(fixtureValue.getValue(), componentType);
                    Object obj = converter.convert(fixtureValue.getValue(), componentType);
                    list.add(obj);
                }

                try {
                    Object[] args = Arrays.copyOf(list.toArray(), list.size(), (Class) paramType.getRawType());
                    candidate.invoke(target, new Object[] { args });
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            return;
        }
        return;
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
