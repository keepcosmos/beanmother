package io.beanmother.core.converter.std;

import io.beanmother.core.converter.AbstractGenericConverter;
import io.beanmother.core.converter.ConverterException;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Converter used to convert a String to a URI
 */
public class StringToURIConverter extends AbstractGenericConverter<String, URI> {

    @Override
    public URI convert(String source) {
        try {
            return new URI(source);
        } catch (URISyntaxException e) {
            throw new ConverterException(source, getTargetTypeToken().getClass(), null, e);
        }
    }
}
