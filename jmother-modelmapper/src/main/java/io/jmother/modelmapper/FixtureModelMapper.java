package io.jmother.modelmapper;

import io.jmother.core.mapper.FixtureMapper;
import io.jmother.modelmapper.converter.DateToJavaTimeConverters;
import org.modelmapper.ModelMapper;

import java.util.Map;

/**
 * The implementation of the {@link FixtureMapper} which allows
 * to convert {@link Map} data to T instance.
 *
 * {@link org.modelmapper.ModelMapper} is used for this implementation.
 */
public class FixtureModelMapper implements FixtureMapper {

    private ModelMapper modelMapper;

    public FixtureModelMapper() {
    }

    public FixtureModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public <T> T map(Map<String, Object> data, Class<T> targetType) {
        return getModelMapper().map(data, targetType);
    }

    @Override
    public <T> T map(Map<String, Object> data, T targetObject) {
        getModelMapper().map(data, targetObject);
        return targetObject;
    }

    public ModelMapper getModelMapper() {
        if (this.modelMapper == null) {
            ModelMapper mapper = new ModelMapper();
            mapper.addConverter(DateToJavaTimeConverters.DATE_TO_LOCAL_DATE);
            mapper.addConverter(DateToJavaTimeConverters.DATE_TO_LOCAL_TIME);
            mapper.addConverter(DateToJavaTimeConverters.DATE_TO_LOCAL_DATETIME);
            this.modelMapper = mapper;
        }
        return this.modelMapper;
    }
}
