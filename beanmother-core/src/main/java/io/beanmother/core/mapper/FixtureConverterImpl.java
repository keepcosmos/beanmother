package io.beanmother.core.mapper;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.common.*;
import io.beanmother.core.converter.Converter;
import io.beanmother.core.converter.ConverterFactory;
import io.beanmother.core.util.ClassUtils;
import io.beanmother.core.util.PrimitiveTypeUtils;
import io.beanmother.core.util.TypeTokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * Default implementation of {@link FixtureConverter}
 */
@SuppressWarnings("unchecked")
public class FixtureConverterImpl implements FixtureConverter {
    private static final Logger logger = LoggerFactory.getLogger(FixtureConverterImpl.class);

    private static final String java8OptionalConverterKlass = "io.beanmother.java8.converter.OptionalTypeFixtureConverter";
    private static final String guavaOptionalConverterKlass = "io.beanmother.guava.converter.OptionalTypeFixtureConverter";

    private MapperMediator mapperMediator;
    private ConverterFactory converterFactory;

    /**
     * Create a FixtureConverterImpl
     * @param mapperMediator mapper mediator
     * @param converterFactory converter factory
     */
    public FixtureConverterImpl(MapperMediator mapperMediator, ConverterFactory converterFactory) {
        this.mapperMediator = mapperMediator;
        this.converterFactory = converterFactory;
    }

    @Override
    public Object convert(FixtureTemplate fixtureTemplate, final TypeToken typeToken) {

        // convert java.util.Optional if it can.
        if (isJava8OptionalTypeToken(typeToken)) {
            FixtureConverter optionalConverter = loadJava8OptionalConverter();
            if (optionalConverter != null) {
                return optionalConverter.convert(fixtureTemplate, typeToken);
            }
        }

        // convert com.google.common.base.Optional if it can.
        if (isGuavaOptionalTypeToken(typeToken)) {
            FixtureConverter optionalConverter = loadGuavaOptionalConverter();
            if (optionalConverter != null) {
                return optionalConverter.convert(fixtureTemplate, typeToken);
            }
        }

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
     */
    public ConverterFactory getConverterFactory() {
        return converterFactory;
    }

    /**
     * Get mapperMediator
     */
    public MapperMediator getMapperMediator() {
        return mapperMediator;
    }

    /**
     * Get fixtureMapper
     */
    public FixtureMapper getFixtureMapper() {
        return getMapperMediator().getFixtureMapper();
    }

    /**
     * Convert the fixtureValue to the given TypeToken
     * @param fixtureValue the FixtureValue
     * @param typeToken the TypeToken
     * @return the converted object from fixtureValue.
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
     * @param fixtureList fixture list
     * @param typeToken type token
     * @return converted object from fixtureList.
     */
    protected Object convert(FixtureList fixtureList, TypeToken<?> typeToken) {
        boolean isArray = typeToken.isArray();
        boolean isList = typeToken.isSubtypeOf(TypeToken.of(List.class));
        boolean isSet = typeToken.isSubtypeOf(TypeToken.of(Set.class));

        if (!isList && !isArray && !isSet) {
            throw new FixtureMappingException("Target setter of '" + fixtureList.getFixtureName() + "' must be List, Set or array.");
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
        if (convertedList.isEmpty()) return null;

        if(isArray) {
            if (elementTypeToken.isPrimitive()) {
                return PrimitiveTypeUtils.toWrapperListToPrimitiveArray(convertedList, (Class<?>) elementTypeToken.getType());
            } else {
                return Arrays.copyOf(convertedList.toArray(), convertedList.size(), (Class) typeToken.getRawType());
            }
        } else if (isSet) {
            return new HashSet<>(convertedList);
        } else {

            return convertedList;
        }
    }

    /**
     * Convert FixtureMap to given type
     * @param fixtureMap fixture Map
     * @param typeToken type token
     * @return converted Object from fixtureMap
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
            Object obj;
            try {
                obj = ConstructHelper.construct(typeToken.getRawType(), fixtureMap, this);
            } catch (Exception e) {
                throw new FixtureMappingException(e);
            }
            getFixtureMapper().map(fixtureMap, obj);
            return obj;
        }
    }

    private boolean isJava8OptionalTypeToken(TypeToken<?> typeToken) {
        return typeToken.getRawType().getName().equals("java.util.Optional");
    }

    private boolean isGuavaOptionalTypeToken(TypeToken<?> typeToken) {
        return typeToken.getRawType().equals(com.google.common.base.Optional.class);
    }

    private FixtureConverter loadFixtureConverter(String className) {
        try {
            Constructor[] constructors = ClassUtils.getDefaultClassLoader().loadClass(className).getConstructors();
            for (Constructor constructor : constructors) {
                if (constructor.getParameterTypes().length == 1 && constructor.getParameterTypes()[0] == FixtureConverter.class) {
                    try {
                        return (FixtureConverter) constructor.newInstance(this);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private FixtureConverter loadJava8OptionalConverter() {
        return loadFixtureConverter(java8OptionalConverterKlass);
    }

    private FixtureConverter loadGuavaOptionalConverter() {
        return loadFixtureConverter(guavaOptionalConverterKlass);
    }
}
