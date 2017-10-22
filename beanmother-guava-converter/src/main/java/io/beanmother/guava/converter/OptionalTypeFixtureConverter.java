package io.beanmother.guava.converter;

import com.google.common.base.Optional;
import com.google.common.reflect.TypeToken;
import io.beanmother.core.common.FixtureTemplate;
import io.beanmother.core.common.FixtureValue;
import io.beanmother.core.mapper.FixtureConverter;
import io.beanmother.core.util.TypeTokenUtils;

import java.util.List;

/**
 * It is a kind of decorate converter about {@link FixtureConverter} for Guava Optional type
 */
public class OptionalTypeFixtureConverter implements FixtureConverter {

    private FixtureConverter fixtureConverter;


    public OptionalTypeFixtureConverter(FixtureConverter converter) {
        this.fixtureConverter = converter;
    }

    /**
     * Convert for a target of Optional
     */
    @Override
    public Object convert(FixtureTemplate fixtureTemplate, TypeToken typeToken) {
        if (typeToken.getRawType() != Optional.class) {
            return fixtureConverter.convert(fixtureTemplate, typeToken);
        }
        List<TypeToken<?>> types = TypeTokenUtils.extractGenericTypeTokens(typeToken);
        if (types.isEmpty()) {
            if (fixtureTemplate instanceof FixtureValue) {
                Object value = ((FixtureValue) fixtureTemplate).getValue();
                return Optional.of(value);
            }
            return null;
        } else {
            return Optional.of(fixtureConverter.convert(fixtureTemplate, types.get(0)));
        }
    }
}
