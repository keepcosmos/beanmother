package io.beanmother.grpc;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import io.beanmother.grpc.util.PatternBuilderClass;
import io.beanmother.grpc.util.PatternBuilderClass.BuilderPC;

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
    public void testBuilderAndAttr() {
    	PatternBuilderClass obj = objectMother.bear("pattern-builder", PatternBuilderClass.class);
    	assertTrue("1".equals(((PatternBuilderClass)obj).getAttr1()));
    }

	@Test
    public void testBuilderAttrNonExisting() {
    	PatternBuilderClass obj = objectMother.bear("pattern-builder-attr-non-existing", PatternBuilderClass.class);
    	assertTrue("1".equals(((PatternBuilderClass)obj).getAttr1()));
    } 

    @Test
    public void testBuilderInitNonExisting() {
    	PatternBuilderClass obj = objectMother.bear("pattern-builder-init-non-existing", PatternBuilderClass.class);
    	assertTrue(null==obj);
    }  

    @Test
    public void testBuilderInitParamNotFound() {
    	PatternBuilderClass obj = objectMother.bear("pattern-builder-init-param-not-found", PatternBuilderClass.class);
    	assertTrue(null==obj);
    } 
    
    @Test
    public void testBuilderFinishNonExisting() {
    	PatternBuilderClass obj = objectMother.bear("pattern-builder-finish-non-existing", PatternBuilderClass.class);
    	assertTrue(null==obj);
    }  

    @Test
    public void testBuilderFinishParamNotFound() {
        BuilderPC obj = objectMother.bear("pattern-builder-finish-param-not-found", BuilderPC.class);
    	assertTrue(null==obj);
    }     

    @Test
    public void testBuilderTargetNonExisting() {
    	PatternBuilderClass obj = objectMother.bear("pattern-builder-targetclass-non-existing", PatternBuilderClass.class);
    	assertTrue(null==obj);
    } 

    @Test
    public void testBuilderTargetNotFound() {
    	PatternBuilderClass obj = objectMother.bear("pattern-builder-targetclass-not-found", PatternBuilderClass.class);
    	assertTrue(null==obj);
    }     

}