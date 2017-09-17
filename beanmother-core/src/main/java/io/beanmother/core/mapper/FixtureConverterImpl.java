package io.beanmother.core.mapper;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.common.*;
import io.beanmother.core.converter.Converter;
import io.beanmother.core.converter.ConverterFactory;
import io.beanmother.core.util.PrimitiveTypeUtils;
import io.beanmother.core.util.TypeTokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Default implementation of {@link FixtureConverter}
 */
@SuppressWarnings("unchecked")
public class FixtureConverterImpl implements FixtureConverter {
    private final static Logger logger = LoggerFactory.getLogger(FixtureConverterImpl.class);

    private MapperMediator mapperMediator;
    private ConverterFactory converterFactory;

    /**
     * Create a FixtureConverterImpl
     * @param mapperMediator
     * @param converterFactory
     */
    public FixtureConverterImpl(MapperMediator mapperMediator, ConverterFactory converterFactory) {
        this.mapperMediator = mapperMediator;
        this.converterFactory = converterFactory;
    }

    @Override
    public Object convert(FixtureTemplate fixtureTemplate, final TypeToken<?> typeToken) {
        final Object[] converted = new Object[1];
        new FixtureTemplateSubTypeHandler() {
            @Override
            protected void handleIf(FixtureMap fixtureMap) {
                converted[0] = convert(fixtureMap, typeToken);
            }

            @Override
            protected void handleIf(FixtureList fixtureList) {
                converted[0] = convert(fixtureList, typeToken);
            }

            @Override
            protected void handleIf(FixtureValue fixtureValue) {
                converted[0] = convert(fixtureValue, typeToken);
            }
        }.handle(fixtureTemplate);
        return converted[0];
    }

    /**
     * Get converterFactory
     * @return
     */
    public ConverterFactory getConverterFactory() {
        return converterFactory;
    }

    /**
     * Get mapperMediator
     * @return
     */
    public MapperMediator getMapperMediator() {
        return mapperMediator;
    }

    /**
     * Get fixtureMapper
     * @return
     */
    public FixtureMapper getFixtureMapper() {
        return getMapperMediator().getFixtureMapper();
    }

    /**
     * Convert the fixtureValue to the given TypeToken
     * @param fixtureValue
     * @param typeToken
     * @return converted object from fixtureValue.
     */
    protected Object convert(FixtureValue fixtureValue, TypeToken<?> typeToken) {
        if (typeToken.isPrimitive()) {
            Class<?> wrapperClass = PrimitiveTypeUtils.toWrapper((Class<?>) typeToken.getType());
            typeToken = TypeToken.of(wrapperClass);
        }

        Object source = fixtureValue.getValue();
        Converter convert = getConverterFactory().get(source, typeToken);

        if (convert != null) {
            return convert.convert(source, typeToken);
        } else {
            return null;
        }
    }

    /**
     * Convert the fixtureList to the given TypeToken
     * @param fixtureList
     * @param typeToken
     * @return converted object from fixtureList.
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    protected Object convert(FixtureList fixtureList, TypeToken<?> typeToken) {
        boolean isArray = typeToken.isArray();
        boolean isList = typeToken.isSubtypeOf(TypeToken.of(List.class));

        if (!isList && !isArray) {
            throw new FixtureMappingException("Target setter of '" + fixtureList.getFixtureName() + "' must be List or array.");
        }

        final List convertedList;
        if (isArray || typeToken.getRawType().isInterface()) {
            convertedList = new ArrayList();
        } else {
            try {
                convertedList = (List) typeToken.getRawType().newInstance();
            } catch (Exception e) {
                throw new FixtureMappingException(e);
            }
        }
        TypeToken<?> elementTypeToken = TypeTokenUtils.extractElementTypeToken(typeToken);


        for (FixtureTemplate template : fixtureList) {
            Object converted = convert(template, elementTypeToken);
            if (converted != null) {
                convertedList.add(converted);
            } else {
                logger.warn("Can not find converter for " + fixtureList.getFixtureName());
            }
        }

        // not found converter
        if (convertedList.size() == 0) return null;

        if(isArray) {
            if (elementTypeToken.isPrimitive()) {
                return PrimitiveTypeUtils.toWrapperListToPrimitiveArray(convertedList, (Class<?>) elementTypeToken.getType());
            } else {
                return Arrays.copyOf(convertedList.toArray(), convertedList.size(), (Class) typeToken.getRawType());
            }
        } else {
            return convertedList;
        }
    }

    /**
     * Convert FixtureMap to given type
     * @param fixtureMap
     * @param typeToken
     * @return converted Object from fixtureMap
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    protected Object convert(FixtureMap fixtureMap, TypeToken<?> typeToken) {
        if (typeToken.isSubtypeOf(Map.class)) {
            final TypeToken<?> keyTypeToken;
            final TypeToken<?> valueTypeToken;
            List<TypeToken<?>> keyValueTypeTokens = TypeTokenUtils.extractGenericTypeTokens(typeToken);
            if (keyValueTypeTokens.size() == 2) {
                keyTypeToken = keyValueTypeTokens.get(0);
                valueTypeToken = keyValueTypeTokens.get(1);
            } else {
                keyTypeToken = TypeToken.of(Object.class);
                valueTypeToken = TypeToken.of(Object.class);
            }


            Map convertedMap;
            if (typeToken.getRawType().isInterface()) {
                convertedMap = new LinkedHashMap();
            } else {
                try {
                    convertedMap = (Map) typeToken.getRawType().newInstance();
                } catch (Exception e) {
                    throw new FixtureMappingException(e);
                }
            }

            for (Map.Entry<String, FixtureTemplate> entry : fixtureMap.entrySet()) {
                Object key = convert(new FixtureValue(entry.getKey()), keyTypeToken);
                Object converted = convert(entry.getValue(), valueTypeToken);
                convertedMap.put(key, converted);
            }
            return convertedMap;
        } else {
            Object obj = null;
            try {
                obj = ConstructHelper.construct(typeToken.getRawType(), fixtureMap, this);
            } catch (Exception e) {
                throw new FixtureMappingException(e);
            }
            getFixtureMapper().map(fixtureMap, obj);
            return obj;
        }
    }
}
