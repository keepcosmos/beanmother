package io.beanmother.core.converter.std;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.AbstractConverter;
import io.beanmother.core.converter.ConverterException;

/**
 * Converter used to convert a String to a Enum
 */
public class StringToEnumConverter extends AbstractConverter {

    @SuppressWarnings("unchecked")
    @Override
    public Object convert(Object source, TypeToken<?> targetTypeToken) {
        if (!canHandle(source, targetTypeToken)) throw new ConverterException(source, targetTypeToken.getRawType(), null);

        Class enumClass = targetTypeToken.getRawType();
        for (Object enumConstant : enumClass.getEnumConstants()) {
            String enumStr = enumConstant.toString().replaceAll("\\_", "");
            String sourceStr = ((String) source).replaceAll("\\-", "").replaceAll("\\_", "").replaceAll("\\s", "");
            if (enumStr.equalsIgnoreCase(sourceStr)) {
                return Enum.valueOf(enumClass, enumConstant.toString());
            }
        }

        throw new ConverterException(source, targetTypeToken.getRawType(), "can not find enum constants");
    }

    @Override
    public boolean canHandle(Object source, TypeToken<?> targetTypeToken) {
        Class enumClass = targetTypeToken.getRawType();
        return (source instanceof String) && enumClass.isEnum();
    }
}
