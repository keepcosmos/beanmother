package io.beanmother.grpc;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import io.beanmother.grpc.util.PatternBuilderClass;

/**
 * Test for {@link GRPCObjectMother}
 */
public class GRPCObjectMotherTest {

	GRPCObjectMother objectMother = GRPCObjectMother.getInstance();
	
    @Before
    public void setup(){
        objectMother.addFixtureLocation("testmodel_fixtures");
    }
	
	@Test
    public void testBuilderException() {
    	PatternBuilderClass obj = objectMother.bear("pattern-builder-nomethod", PatternBuilderClass.class);
    	assertTrue(null==obj);
    }  
    
    @Test
    public void testBuilderAndAttr() {
    	PatternBuilderClass obj = objectMother.bear("pattern-builder", PatternBuilderClass.class);
    	assertTrue("1".equals(((PatternBuilderClass)obj).getAttr1()));
    }       
}