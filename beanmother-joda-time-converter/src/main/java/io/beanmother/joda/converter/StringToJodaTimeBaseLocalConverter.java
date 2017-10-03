package io.beanmother.joda.converter;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.AbstractConverter;
import io.beanmother.core.converter.ConverterException;
import io.beanmother.core.converter.std.StringToDateConverter;
import org.joda.time.base.BaseLocal;

import java.util.Date;

/**
 * String to BaseLocal({@link org.joda.time.LocalDate}, {@link org.joda.time.LocalTime} and {@link org.joda.time.LocalDateTime} converter.
 */
public class StringToJodaTimeBaseLocalConverter extends AbstractConverter {

    private final static StringToDateConverter stringToDateConverter = new StringToDateConverter();
    private final static DateToJodaTimeBaseLocalConverter dateToJodaTimeBaseLocalConverter = new DateToJodaTimeBaseLocalConverter();

    @Override
    public Object convert(Object source, TypeToken<?> targetTypeToken) {
        if (!canHandle(source, targetTypeToken)) {
            throw new ConverterException(source, targetTypeToken.getRawType());
        }

        Date date = stringToDateConverter.convert(String.valueOf(source));
        return dateToJodaTimeBaseLocalConverter.convert(date, targetTypeToken);
    }

    @Override
    public boolean canHandle(Object source, TypeToken<?> targetTypeToken) {
        return targetTypeToken.isSubtypeOf(BaseLocal.class) && stringToDateConverter.canHandle(source, TypeToken.of(Date.class));
    }
}
