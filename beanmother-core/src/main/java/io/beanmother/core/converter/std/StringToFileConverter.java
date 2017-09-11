package io.beanmother.core.converter.std;

import io.beanmother.core.converter.AbstractGenericConverter;

import java.io.File;

/**
 * Converter used to convert a String to a File
 */
public class StringToFileConverter extends AbstractGenericConverter<String, File> {

    @Override
    public File convert(String source) {
        return new File(source);
    }
}
