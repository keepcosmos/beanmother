package io.beanmother.core.mapper;

import io.beanmother.core.mapper.converter.ConverterFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Mapping property value by setter. Also support add prefix method if exists for array
 */
public class SetterMapper extends AbstractPropertyMapper {

    private static String SETTER_PREFIX = "set";
    private static String ADD_PREFIX = "add";

    public SetterMapper(ConverterFactory converterFactory) {
        super(converterFactory);
    }

    @Override
    public void map(Object target, String key, Object value) {
        List<Method> methods = findSetterMethods(target, key);
    }

    private void map(Object target, String key, List<Object> value) {

    }

    private void map(Map<Object, Object> target, String key, Map<Object, Object> value) {

    }

    private void map(List<Object> target, String key, List<Object> value) {

    }

    private void map(Object[] target, String key, List<Object> value) {

    }

    private List<Method> findSetterMethods(Object target, String key) {
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
