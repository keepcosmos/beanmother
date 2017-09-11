package io.beanmother.core.converter.std;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.AbstractConverter;
import io.beanmother.core.util.NumberUtils;
import io.beanmother.core.util.PrimitiveTypeUtils;

/**
 * Converter used to convert a String to a Number
 */
@SuppressWarnings("unchecked")
public class StringToNumberConverter extends AbstractConverter {

    @Override
    public Object convert(Object source, TypeToken<?> targetTypeToken) {
        if (targetTypeToken.isPrimitive()) {
            targetTypeToken = PrimitiveTypeUtils.toWrapperTypeToken(targetTypeToken);
        }

        if (canHandle(source, targetTypeToken)) {
            return NumberUtils.parseNumber((String) source, (Class) targetTypeToken.getType());
        } else {
            throw new IllegalArgumentException("can not convert '" + source + "' to " + targetTypeToken.getRawType());
        }
    }

    @Override
    public boolean canHandle(Object source, TypeToken<?> targetTypeToken) {
        if (targetTypeToken.isPrimitive()) {
            targetTypeToken = PrimitiveTypeUtils.toWrapperTypeToken(targetTypeToken);
        }
        if (!targetTypeToken.isSubtypeOf(Number.class)) return false;

        if (!(source instanceof String)) return false;

        try {
            NumberUtils.parseNumber((String) source, (Class) targetTypeToken.getType());
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }
}
