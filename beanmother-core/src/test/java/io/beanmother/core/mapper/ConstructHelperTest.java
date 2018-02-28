package io.beanmother.core.mapper;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import io.beanmother.core.ObjectMother;
import io.beanmother.core.common.FixtureMap;
import io.beanmother.core.converter.ConverterFactory;
import io.beanmother.core.loader.Location;
import io.beanmother.core.loader.store.DefaultFixturesStore;
import io.beanmother.core.loader.store.FixturesStore;
import io.beanmother.core.mapper.ConstructHelperTest.PatternBuilderClass.BuilderPC;
import io.beanmother.testmodel.Price;

/**
 * Test for {@link ConstructHelper}
 */
public class ConstructHelperTest {

    FixturesStore store = new DefaultFixturesStore();
    FixtureConverter fixtureConverter = new DefaultFixtureMapper(new ConverterFactory()).getFixtureConverter();

    ObjectMother objectMother = ObjectMother.getInstance();
    
    @Before
    public void setup(){
        store.addLocation(new Location("testmodel_fixtures"));
        objectMother.addFixtureLocation("testmodel_fixtures");
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
        Object obj = ConstructHelper.construct(PatternBuilderClass.class, fixtureMap, fixtureConverter);

        assertTrue(obj instanceof BuilderPC);
    }
    
    @Test
    public void testBuilderAndAttr() {
    	PatternBuilderClass obj = objectMother.bear("pattern-builder", PatternBuilderClass.class);
    	assertTrue("1".equals(((PatternBuilderClass)obj).attr1));
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
    
    public static final class PatternBuilderClass {
    	
    	private String attr1;
    	
		public static BuilderPC newBuilder() {
			return new BuilderPC();
    	}
    	
    	public static final class BuilderPC {
    		
    		private static PatternBuilderClass pbc;
    		
    		private BuilderPC() {
    			pbc = new PatternBuilderClass();
    		}
    		
        	public BuilderPC setAttr1(String value) {
        		pbc.attr1=value;
        		return this;
        	}
    		
        	public PatternBuilderClass build() {
        		return pbc;
        	}
    	}
    	
    	private PatternBuilderClass() {
    		attr1 = "";
    	}

    	public String getAttr1() {
			return attr1;
		}
    	
    }

	public static void main(String[] args) {
		PatternBuilderClass pbc = PatternBuilderClass.newBuilder().setAttr1("attr1").build();
		System.out.println(pbc.getAttr1());
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