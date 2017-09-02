package io.beanmother.core.converter.std;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.AbstractConverter;
import io.beanmother.core.util.NumberUtils;
import io.beanmother.core.util.PrimitiveTypeUtils;

public class NumberToNumberConverter extends AbstractConverter {
    
    @Override
    public Object convert(Object source, TypeToken<?> targetTypeToken) {
        if (targetTypeToken.isPrimitive()) {
            targetTypeToken = convertToWrapperTypeToken(targetTypeToken);
        }
        return NumberUtils.convertNumberToTargetClass((Number) source, (Class) targetTypeToken.getType());
    }

    @Override
    public boolean canHandle(Object source, TypeToken<?> targetTypeToken) {
        if (targetTypeToken.isPrimitive()) {
            targetTypeToken = convertToWrapperTypeToken(targetTypeToken);
        }
        return TypeToken.of(source.getClass()).isSubtypeOf(Number.class)
                && targetTypeToken.isSubtypeOf(Number.class);
    }

    private TypeToken<?> convertToWrapperTypeToken(TypeToken<?> typeToken) {
        if (typeToken.isPrimitive()) {
            Class<?> wrapperType = PrimitiveTypeUtils.toWrapper((Class<?>) typeToken.getType());
            return typeToken.of(wrapperType);
        } else {
            return typeToken;
        }
    }
}
