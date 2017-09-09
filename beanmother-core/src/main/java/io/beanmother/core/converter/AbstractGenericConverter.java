package io.beanmother.core.converter;

import com.google.common.reflect.TypeToken;

/**
 * An abstract converter for checking with generic types.
 * @param <S> source type
 * @param <D> destination type
 */
public abstract class AbstractGenericConverter<S, D> extends AbstractConverter {

    private TypeToken<S> sourceTypeToken = new TypeToken<S>(getClass()) {};
    private TypeToken<D> targetTypeToken = new TypeToken<D>(getClass()) {};

    public AbstractGenericConverter() {
        super();
    }

    public AbstractGenericConverter(int priority) {
        super(priority);
    }

    @Override
    public Object convert(Object source, TypeToken<?> typeToken) {
        if (!canHandle(source, typeToken)) throw new ConverterException(source, typeToken.getRawType(), null);
        return convert((S) source);
    }

    protected abstract D convert(S source);

    @Override
    public boolean canHandle(Object source, TypeToken<?> typeToken) {
        return sourceTypeToken.getRawType().equals(source.getClass())
                && this.targetTypeToken.equals(typeToken);
    }

    public TypeToken<S> getSourceTypeToken() {
        return sourceTypeToken;
    }

    public TypeToken<D> getTargetTypeToken() {
        return targetTypeToken;
    }
}
