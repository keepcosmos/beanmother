package io.beanmother.core.mapper;

import io.beanmother.core.mapper.converter.ConverterFactory;

public class ObjectMappingResolver implements PropertyMapper {

    /**
     * Enum for MappingType
     *
     * SETTER: only use setter for mapping.
     * FIELD: only use field for mapping.
     * SETTER_AND_FIELD: use field as a fallback when can not use setter method.
     */
    public enum MappingType {
        SETTER, FIELD, SETTER_AND_FIELD
    }

    private MappingType mappingType;

    private ConverterFactory converterFactory;

    private SetterMapper setterMapper;

    private FieldMapper fieldMapper;

    public ObjectMappingResolver() {
        this(MappingType.SETTER_AND_FIELD, new ConverterFactory());
    }

    public ObjectMappingResolver(MappingType mappingType) {
        this(mappingType, new ConverterFactory());
        this.mappingType = mappingType;
        this.converterFactory = new ConverterFactory();
    }

    public ObjectMappingResolver(ConverterFactory converterFactory) {
        this(MappingType.SETTER_AND_FIELD, converterFactory);
    }

    public ObjectMappingResolver(MappingType mappingType, ConverterFactory converterFactory) {
        this.mappingType = mappingType;
        this.converterFactory = converterFactory;
        this.setterMapper = new SetterMapper(converterFactory);
        this.fieldMapper = new FieldMapper(converterFactory);
    }

//    @Override
//    public Object map(Class targetType, Map<String, Object> value) {
//        Object target = null;
//        for (Map.Entry<String, Object> entry : value.entrySet()) {
//            map(target, entry.getKey(), entry.getValue());
//        }
//        return target;
//    }

    @Override
    public void map(Object target, String key, Object value) {
        if (isSetterMapping()) {
            setterMapper.map(target, key, value);
        } else if (isFieldMapping()) {
            fieldMapper.map(target, key, value);
        } else if (isSetterAndFieldMapping()) {
            try {
                setterMapper.map(target, key, value);
            } catch (FixtureMappingException e) {
                fieldMapper.map(target, key, value);
            }
        }

    }

    private boolean isSetterMapping() {
        return this.mappingType == MappingType.SETTER;
    }

    private boolean isFieldMapping() {
        return this.mappingType == MappingType.FIELD;
    }

    private boolean isSetterAndFieldMapping() {
        return this.mappingType == MappingType.SETTER_AND_FIELD;
    }
}
