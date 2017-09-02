package io.beanmother.core.mapper;

import io.beanmother.core.fixture.FixtureTemplate;
import io.beanmother.core.converter.ConverterFactory;
import io.beanmother.core.mapper.setter.SetterMapperMediator;

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

    private SetterMapperMediator setterMapperMediator;

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
        this.setterMapperMediator = new SetterMapperMediator(converterFactory);
        this.fieldMapper = new FieldMapper(converterFactory);
    }

    @Override
    public void map(Object target, String key, FixtureTemplate value) {
        if (isSetterMapping()) {
//            setterMapperMediator.map(target, key, value);
        } else if (isFieldMapping()) {
            fieldMapper.map(target, key, value);
        } else if (isSetterAndFieldMapping()) {
            try {
//                setterMapperMediator.map(target, key, value);
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
