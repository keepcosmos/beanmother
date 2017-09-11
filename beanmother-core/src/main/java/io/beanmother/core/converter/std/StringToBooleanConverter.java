package io.beanmother.core.converter.std;

import io.beanmother.core.converter.AbstractGenericConverter;
import io.beanmother.core.converter.ConverterException;

import java.util.HashSet;
import java.util.Set;

/**
 * Converter used to convert a String to a Boolean
 */
public class StringToBooleanConverter extends AbstractGenericConverter<String, Boolean> {

    private final static Set<String> TRUE_STRING;
    private final static Set<String> FALSE_STRING;

    static {
        TRUE_STRING = new HashSet<>();
        TRUE_STRING.add("true");
        TRUE_STRING.add("t");
        TRUE_STRING.add("yes");
        TRUE_STRING.add("y");

        FALSE_STRING = new HashSet<>();
        FALSE_STRING.add("false");
        FALSE_STRING.add("f");
        FALSE_STRING.add("no");
        FALSE_STRING.add("n");
    }

    @Override
    public Boolean convert(String source) {
        String normalize = source.trim().toLowerCase();
        if (TRUE_STRING.contains(normalize)) {
            return true;
        } else if (FALSE_STRING.contains(normalize)) {
            return false;
        } else {
            throw new ConverterException(source, getTargetTypeToken().getRawType(), null);
        }
    }
}
