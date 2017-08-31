package io.beanmother.core.converter.std;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.AbstractConverter;
import io.beanmother.core.util.NumberUtils;

public class NumberToNumberConverter extends AbstractConverter {
    
    @Override
    public Object convert(Object source, TypeToken<?> typeToken) {
        return NumberUtils.convertNumberToTargetClass((Number) source, (Class) typeToken.getType());
    }

    @Override
    public boolean canHandle(Object source, TypeToken<?> targetTypeToken) {
        return TypeToken.of(source.getClass()).isSubtypeOf(Number.class)
                && targetTypeToken.isSubtypeOf(Number.class);
    }
}
