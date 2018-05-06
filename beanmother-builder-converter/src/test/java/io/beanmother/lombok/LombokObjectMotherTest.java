package io.beanmother.lombok;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import io.beanmother.builder.BuilderObjectMother;

import io.beanmother.lombok.BuilderClass;

/**
 * Test for {@link BuilderObjectMother}
 */
public class LombokObjectMotherTest {

	BuilderObjectMother objectMother = BuilderObjectMother.getInstance();
	
    @Before
    public void setup(){
        objectMother.addFixtureLocation("testmodel_fixtures");
    }
	
    @Test
    public void testBuilderAndAttr() {
    	BuilderClass obj = objectMother.bear("pattern-builder-lombok", BuilderClass.class);
    	assertTrue("1".equals(((BuilderClass)obj).getProp1()));
    	assertTrue("2".equals(((BuilderClass)obj).getProp2()));
    }

}