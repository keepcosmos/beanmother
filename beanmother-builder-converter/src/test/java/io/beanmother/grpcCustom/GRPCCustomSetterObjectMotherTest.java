package io.beanmother.grpcCustom;

import io.beanmother.builder.BuilderObjectMother;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link BuilderObjectMother}
 */
public class GRPCCustomSetterObjectMotherTest {

    private BuilderObjectMother objectMother = BuilderObjectMother.getInstance();

    @Before
    public void setup() {
        objectMother.addFixtureLocation("testmodel_fixtures");
    }

    @Test
    public void testBuilderAndAttr() {
        GrpcBuilderCustomSetterClass obj = objectMother.bear("pattern-builder-custom-setter", GrpcBuilderCustomSetterClass.class);
        assertEquals("1", obj.getAttr1());
    }

    @Test
    public void testBuilderAttrNonExisting() {
        GrpcBuilderCustomSetterClass obj = objectMother.bear("pattern-builder-custom-setter-non-existing", GrpcBuilderCustomSetterClass.class);
        assertEquals("1", obj.getAttr1());
    }

}