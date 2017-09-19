package io.beanmother.core;

import io.beanmother.testmodel.Coffee;
import io.beanmother.testmodel.Price;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link ObjectMother}
 *
 * It should be full test.
 */
public class ObjectMotherTest {

    ObjectMother objectMother = ObjectMother.getInstance();

    @Before
    public void setup() {
        objectMother.addFixtureLocation("testmodel_fixtures");
    }

    @Test
    public void testBearCoffee() {
        Coffee blueMountain = objectMother.bear("blue_mountain", Coffee.class);
        assertEquals(1l, blueMountain.id);
        assertEquals(new Price(1, Price.Currency.USD), blueMountain.price);
        assertEquals(Coffee.Bean.BlueMountain, blueMountain.bean);
        assertEquals("MyCoffee", blueMountain.seller);

        Coffee java = objectMother.bear("java", Coffee.class);
        assertEquals(3l, java.id);
        assertEquals(new Price(2.1f, Price.Currency.KRW), java.price);
        assertEquals(Coffee.Bean.Java, java.bean);
        assertEquals("Gosling's Coffee", java.seller);
    }

    @Test
    public void testLoadSports() {

    }
}