package io.beanmother.core.converter.std;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.AbstractConverter;

/**
 * Converter used to convert a any Object to a String
 */
public class ObjectToStringConverter extends AbstractConverter {

    public ObjectToStringConverter() {
    }

    public ObjectToStringConverter(int priority) {
        super(priority);
    }

    @Override
    public Object convert(Object source, TypeToken<?> targetTypeToken) {
        return String.valueOf(source);
    }

    @Override
    public boolean canHandle(Object source, TypeToken<?> targetTypeToken) {
        return (source != null) && targetTypeToken.isSubtypeOf(String.class);
    }
}
