package io.beanmother.core.mapper.converter;

public interface Converter<S, D> {
    D convert(S source);
}
