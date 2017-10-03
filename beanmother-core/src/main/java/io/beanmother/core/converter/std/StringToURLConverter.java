package io.beanmother.core.converter.std;

import io.beanmother.core.converter.AbstractGenericConverter;
import io.beanmother.core.converter.ConverterException;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Converter used to convert a String to a URL
 */
public class StringToURLConverter extends AbstractGenericConverter<String, URL> {

    @Override
    public URL convert(String source) {
        try {
            return new URL(source);
        } catch (MalformedURLException e) {
            throw new ConverterException(source, getTargetTypeToken().getRawType(), e);
        }
    }
}
