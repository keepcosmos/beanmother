package io.beanmother.core.mapper;

/**
 * Mediator for {@link FixtureMapper}.
 *
 * Generally, FixtureMapper has a {@link FixtureConverter} and FixtureConverter has a FixtureMapper.
 * MapperMediator solves circular reference problem between FixtureMapper and FixtureConverter.
 */
public interface MapperMediator {

    /**
     * Get FixtureMapper
     */
    FixtureMapper getFixtureMapper();

    /**
     * Get FixtureConverter
     */
    FixtureConverter getFixtureConverter();
}
