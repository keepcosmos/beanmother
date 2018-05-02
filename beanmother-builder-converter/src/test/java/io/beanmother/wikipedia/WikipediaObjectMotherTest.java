package io.beanmother.wikipedia;


import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import io.beanmother.builder.BuilderObjectMother;

import io.beanmother.wikipedia.Car;
import io.beanmother.wikipedia.CarBuilderImpl;

/**
 * Test for {@link BuilderObjectMother}
 */
public class WikipediaObjectMotherTest {

	BuilderObjectMother objectMother = BuilderObjectMother.getInstance();
	
    @Before
    public void setup(){
        objectMother.addFixtureLocation("testmodel_fixtures");
    }
	
    @Test
    public void testBuilderAndAttr() {
    	Car obj = objectMother.bear("pattern-builder-wikipedia", Car.class);
        assertTrue("Red".equals(obj.getColor()));
    	assertTrue(4==obj.getWheels());
    }

}