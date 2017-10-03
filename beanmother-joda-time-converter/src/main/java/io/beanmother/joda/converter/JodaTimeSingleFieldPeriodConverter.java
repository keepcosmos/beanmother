package io.beanmother.joda.converter;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.AbstractConverter;
import io.beanmother.core.converter.ConverterException;
import io.beanmother.core.converter.std.NumberToNumberConverter;
import io.beanmother.core.converter.std.StringToNumberConverter;
import org.joda.time.*;
import org.joda.time.base.BaseSingleFieldPeriod;

import java.lang.reflect.Type;

/**
 * Converter for {@link org.joda.time.base.BaseSingleFieldPeriod} from Number or String.
 */
public class JodaTimeSingleFieldPeriodConverter extends AbstractConverter {

    private final static StringToNumberConverter stringToNumberConverter = new StringToNumberConverter();
    private final static NumberToNumberConverter numberToNumberConverter = new NumberToNumberConverter();

    @Override
    public Object convert(Object source, TypeToken<?> targetTypeToken) {
        if (!canHandle(source, targetTypeToken)) {
            throw new ConverterException(source, targetTypeToken.getRawType());
        }

        Integer period = null;
        if (source instanceof Number) {
            period = (Integer) numberToNumberConverter.convert(source, TypeToken.of(Integer.class));
        } else if (source instanceof String) {
            period = (Integer) stringToNumberConverter.convert(source, TypeToken.of(Integer.class));
        }

        Type targetType = targetTypeToken.getType();

        if (targetType.equals(Seconds.class)) {
            return Seconds.seconds(period);
        } else if (targetType.equals(Minutes.class)) {
            return Minutes.minutes(period);
        } else if (targetType.equals(Hours.class)) {
            return Hours.hours(period);
        } else if (targetType.equals(Days.class)) {
            return Days.days(period);
        } else if (targetType.equals(Weeks.class)) {
            return Weeks.weeks(period);
        } else if (targetType.equals(Months.class)) {
            return Months.months(period);
        } else if (targetType.equals(Years.class)) {
            return Years.years(period);
        }

        throw new ConverterException(source, targetTypeToken.getRawType());
    }

    @Override
    public boolean canHandle(Object source, TypeToken<?> targetTypeToken) {
        return targetTypeToken.isSubtypeOf(BaseSingleFieldPeriod.class)
                && ((source instanceof Number) || (stringToNumberConverter.canHandle(source, TypeToken.of(Long.class))));
    }
}
