package io.beanmother.core.mapper;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import io.beanmother.core.common.FixtureMap;
import io.beanmother.core.converter.ConverterFactory;
import io.beanmother.core.loader.Location;
import io.beanmother.core.loader.store.DefaultFixturesStore;
import io.beanmother.core.loader.store.FixturesStore;
import io.beanmother.testmodel.Price;

/**
 * Test for {@link ConstructHelper}
 */
public class ConstructHelperTest {

    FixturesStore store = new DefaultFixturesStore();
    FixtureConverter fixtureConverter = new DefaultFixtureMapper(new ConverterFactory()).getFixtureConverter();

    @Before
    public void setup(){
        store.addLocation(new Location("testmodel_fixtures"));
    }

    @Test
    public void testNoArgConstructor() {
        Object obj = ConstructHelper.construct(NoArgConstructorClass.class, new FixtureMap(), fixtureConverter);
        assertTrue(obj instanceof NoArgConstructorClass);
    }

    @Test
    public void testSingleArgConstructor() {
        FixtureMap fixtureMap = store.reproduce("single-arg-constructor");
        Object obj = ConstructHelper.construct(SingleArgsConstuctorClass.class, fixtureMap, fixtureConverter);

        assertTrue(obj instanceof SingleArgsConstuctorClass);
    }

    @Test
    public void testBuilderConstructor() {
    	FixtureMap fixtureMap = store.reproduce("pattern-builder");
        Object obj = ConstructHelper.construct(BuilderClass.class, fixtureMap, fixtureConverter);

        assertTrue(obj instanceof BuilderClass);
    }

    @Test
    public void testMultipleArgsConstructor() {
        FixtureMap fixtureMap = store.reproduce("multiple-args-constructor");
        Object obj = ConstructHelper.construct(MultipleArgConstructorClass.class, fixtureMap, fixtureConverter);

        assertTrue(obj instanceof MultipleArgConstructorClass);
    }

    @Test
    public void testBeanArgConstructor() {
        FixtureMap fixtureMap = store.reproduce("bean-constructor");
        Object obj = ConstructHelper.construct(BeanArgConstructorClass.class, fixtureMap, fixtureConverter);

        assertTrue(obj instanceof BeanArgConstructorClass);
    }


    public static class NoArgConstructorClass {

    }

    public static class SingleArgsConstuctorClass {
        private String str;

        public SingleArgsConstuctorClass(String str) {
            this.str = str;
        }

        public String getStr() {
            return str;
        }
    }
    
    public static class BuilderClass {
    	private BuilderClass() {
    	}

    	public static BuilderClass build() {
    		return new BuilderClass();
    	}
    
    }

    public static class SingleArgAndNoArgConstuctorClass {
        private String str;

        public SingleArgAndNoArgConstuctorClass () {}

        public SingleArgAndNoArgConstuctorClass (String str) {
            this.str = str;
        }

        public String getStr() {
            return str;
        }
    }

    public static class MultipleArgConstructorClass {
        private String str;
        private Integer integer;

        public MultipleArgConstructorClass(String str, Integer integer) {
            this.str = str;
            this.integer = integer;
        }

        public Integer getInteger() {
            return integer;
        }

        public String getStr() {
            return str;
        }
    }

    public static class BeanArgConstructorClass {
        private Price price;

        public BeanArgConstructorClass(Price price) {
            this.price = price;
        }

        public Price getPrice() {
            return price;
        }
    }
}