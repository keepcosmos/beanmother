package io.beanmother.core.mapper;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.Converter;
import io.beanmother.core.converter.ConverterFactory;
import io.beanmother.core.fixture.FixtureList;
import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.fixture.FixtureTemplate;
import io.beanmother.core.fixture.FixtureValue;
import io.beanmother.core.util.PrimitiveTypeUtils;
import io.beanmother.core.util.TypeTokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Default implementation of {@link FixtureConverter}
 */
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
    public Object convert(FixtureTemplate fixtureTemplate, TypeToken<?> typeToken) {
        try {
            if (fixtureTemplate instanceof FixtureMap) {
                return convert((FixtureMap) fixtureTemplate, typeToken);
            } else if (fixtureTemplate instanceof FixtureList) {
                return convert((FixtureList) fixtureTemplate, typeToken);
            } else if (fixtureTemplate instanceof FixtureValue) {
                return convert((FixtureValue) fixtureTemplate, typeToken);
            }
        } catch (Exception e) {
            throw new FixtureMappingException(e);
        }
        return null;
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
    protected Object convert(FixtureList fixtureList, TypeToken<?> typeToken) throws IllegalAccessException, InstantiationException {
        boolean isArray = typeToken.isArray();
        boolean isList = typeToken.isSubtypeOf(TypeToken.of(List.class));

        if (!isList && !isArray) {
            throw new FixtureMappingException("Target setter of '" + fixtureList.getFixtureName() + "' must be List or array.");
        }

        final List convertedList;
        if (isArray || typeToken.getRawType().isInterface()) {
            convertedList = new ArrayList();
        } else {
            convertedList = (List) typeToken.getRawType().newInstance();
        }
        TypeToken<?> elementTypeToken = TypeTokenUtils.extractElementTypeToken(typeToken);


        for (FixtureTemplate template : fixtureList) {
            Object converted;
            if (template instanceof FixtureMap) {
                converted = convert((FixtureMap) template, elementTypeToken);
            } else if (template instanceof FixtureList) {
                converted = convert((FixtureList) template, elementTypeToken);
            } else if (template instanceof FixtureValue) {
                converted = convert((FixtureValue) template, elementTypeToken);
            } else {
                converted = null;
            }

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
    protected Object convert(FixtureMap fixtureMap, TypeToken<?> typeToken) throws IllegalAccessException, InstantiationException {
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
                convertedMap = (Map) typeToken.getRawType().newInstance();
            }

            for (Map.Entry<String, FixtureTemplate> entry : fixtureMap.entrySet()) {
                Object key = convert(new FixtureValue(entry.getKey()), keyTypeToken);
                Object value = null;
                if (entry.getValue() instanceof FixtureMap) {
                    value = convert((FixtureMap) entry.getValue(), valueTypeToken);
                } else if (entry.getValue() instanceof FixtureList) {
                    value = convert((FixtureList) entry.getValue(), valueTypeToken);
                } else if (entry.getValue() instanceof FixtureValue) {
                    value = convert((FixtureValue) entry.getValue(), valueTypeToken);
                }
                convertedMap.put(key, value);
            }
            return convertedMap;
        } else {
            Object obj = typeToken.getRawType().newInstance();
            getFixtureMapper().map(fixtureMap, obj);
            return obj;
        }
    }
}
