package io.beanmother.core.converter.std;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import io.beanmother.core.converter.AbstractGenericConverter;

import java.util.Date;
import java.util.List;

/**
 * {@link String} to {@link Date} converter.
 * The converter uses natty library {@see http://natty.joestelmach.com}.
 */
public class StringToDateConverter extends AbstractGenericConverter<String, Date> {
    private final static Parser dateParser = new Parser();

    @Override
    public Date convert(String source) {
        List<DateGroup> groups = dateParser.parse(source);
        if (groups.size() > 0 && groups.get(0).getDates().size() > 0) {
            return groups.get(0).getDates().get(0);
        }
        throw new IllegalArgumentException("can not convert '" + source + "' to Date");
    }
}
