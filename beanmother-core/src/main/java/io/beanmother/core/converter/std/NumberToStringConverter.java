package io.beanmother.core.converter.std;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.AbstractConverter;

public class NumberToStringConverter extends AbstractConverter {
    @Override
    public Object convert(Object source, TypeToken<?> targetTypeToken) {
        return source.toString();
    }

    @Override
    public boolean canHandle(Object source, TypeToken<?> targetTypeToken) {
        return (source instanceof Number) && targetTypeToken.isSubtypeOf(String.class);
    }
}
