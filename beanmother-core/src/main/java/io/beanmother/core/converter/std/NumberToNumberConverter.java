package io.beanmother.core.converter.std;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.AbstractConverter;
import io.beanmother.core.util.NumberUtils;
import io.beanmother.core.util.PrimitiveTypeUtils;

/**
 * Converter used to convert a Subclass of Number to a Subclass of Number
 */
public class NumberToNumberConverter extends AbstractConverter {
    
    @Override
    public Object convert(Object source, TypeToken<?> targetTypeToken) {
        if (targetTypeToken.isPrimitive()) {
            targetTypeToken = PrimitiveTypeUtils.toWrapperTypeToken(targetTypeToken);
        }
        return NumberUtils.convertNumberToTargetClass((Number) source, (Class) targetTypeToken.getType());
    }

    @Override
    public boolean canHandle(Object source, TypeToken<?> targetTypeToken) {
        if (targetTypeToken.isPrimitive()) {
            targetTypeToken = PrimitiveTypeUtils.toWrapperTypeToken(targetTypeToken);
        }
        return TypeToken.of(source.getClass()).isSubtypeOf(Number.class)
                && targetTypeToken.isSubtypeOf(Number.class);
    }
}
