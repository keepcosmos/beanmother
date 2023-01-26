package io.beanmother.core.converter.std;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import io.beanmother.core.converter.AbstractGenericConverter;
import io.beanmother.core.converter.ConverterException;

import java.util.Date;
import java.util.List;

/**
 * {@link String} to {@link Date} converter.
 * The converter uses natty library
 *
 * @see <a href="http://natty.joestelmach.com">natty</a>
 */
public class StringToDateConverter extends AbstractGenericConverter<String, Date> {
    private static final Parser dateParser = new Parser();

    @Override
    public Date convert(String source) {
        List<DateGroup> groups = dateParser.parse(source);
        if (!groups.isEmpty() && !groups.get(0).getDates().isEmpty()) {
            return groups.get(0).getDates().get(0);
        }
        throw new ConverterException("can not convert '" + source + "' to java.util.Date");
    }
}
