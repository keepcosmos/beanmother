package io.beanmother.grpc;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import io.beanmother.builder.BuilderObjectMother;

import io.beanmother.grpc.GrpcBuilderClass;
import io.beanmother.grpc.GrpcBuilderClass.BuilderPC;

/**
 * Test for {@link GRPCObjectMother}
 */
public class GRPCObjectMotherTest {

	BuilderObjectMother objectMother = BuilderObjectMother.getInstance();
	
    @Before
    public void setup(){
        objectMother.addFixtureLocation("testmodel_fixtures");
    }
	
    @Test
    public void testBuilderAndAttr() {
    	GrpcBuilderClass obj = objectMother.bear("pattern-builder", GrpcBuilderClass.class);
    	assertTrue("1".equals(((GrpcBuilderClass)obj).getAttr1()));
    }

	@Test
    public void testBuilderAttrNonExisting() {
    	GrpcBuilderClass obj = objectMother.bear("pattern-builder-attr-non-existing", GrpcBuilderClass.class);
    	assertTrue("1".equals(((GrpcBuilderClass)obj).getAttr1()));
    } 

    @Test
    public void testBuilderInitNonExisting() {
    	GrpcBuilderClass obj = objectMother.bear("pattern-builder-init-non-existing", GrpcBuilderClass.class);
    	assertTrue(null==obj);
    }  

    @Test
    public void testBuilderInitParamNotFound() {
    	GrpcBuilderClass obj = objectMother.bear("pattern-builder-init-param-not-found", GrpcBuilderClass.class);
    	assertTrue(null==obj);
    } 
    
    @Test
    public void testBuilderFinishNonExisting() {
    	GrpcBuilderClass obj = objectMother.bear("pattern-builder-finish-non-existing", GrpcBuilderClass.class);
    	assertTrue(null==obj);
    }  

    @Test
    public void testBuilderFinishParamNotFound() {
        BuilderPC obj = objectMother.bear("pattern-builder-finish-param-not-found", BuilderPC.class);
    	assertTrue(null==obj);
    }     

    @Test
    public void testBuilderTargetNonExisting() {
    	GrpcBuilderClass obj = objectMother.bear("pattern-builder-targetclass-non-existing", GrpcBuilderClass.class);
    	assertTrue(null==obj);
    } 

    @Test
    public void testBuilderTargetNotFound() {
    	GrpcBuilderClass obj = objectMother.bear("pattern-builder-targetclass-not-found", GrpcBuilderClass.class);
    	assertTrue(null==obj);
    }     

}