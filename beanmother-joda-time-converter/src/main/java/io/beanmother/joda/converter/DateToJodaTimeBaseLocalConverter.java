package io.beanmother.joda.converter;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.AbstractConverter;
import io.beanmother.core.converter.ConverterException;
import org.joda.time.base.BaseLocal;

import java.lang.reflect.Constructor;
import java.util.Date;

/**
 * Date to BaseLocal({@link org.joda.time.LocalDate}, {@link org.joda.time.LocalTime} and {@link org.joda.time.LocalDateTime} converter.
 */
public class DateToJodaTimeBaseLocalConverter extends AbstractConverter {

    @SuppressWarnings("unchecked")
    @Override
    public Object convert(Object source, TypeToken<?> targetTypeToken) {
        if (!canHandle(source, targetTypeToken)) {
            throw new ConverterException(source, targetTypeToken.getRawType());
        }

        Constructor constructor;
        try {
            constructor = ((Class) targetTypeToken.getType()).getConstructor(Object.class);
        } catch (NoSuchMethodException e) {
            throw new ConverterException(source, targetTypeToken.getRawType(), e);
        }
        try {
            return constructor.newInstance(source);
        } catch (Exception e) {
            throw new ConverterException(source, targetTypeToken.getRawType(), e);
        }
    }

    @Override
    public boolean canHandle(Object source, TypeToken<?> targetTypeToken) {
        return targetTypeToken.isSubtypeOf(BaseLocal.class) && (source instanceof Date);
    }
}
