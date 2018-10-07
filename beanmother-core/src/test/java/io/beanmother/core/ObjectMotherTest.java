package io.beanmother.core;

import io.beanmother.core.common.FixtureMap;
import io.beanmother.core.postprocessor.PostProcessor;
import io.beanmother.testmodel.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

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
    public void testBearCoffeeWithExistingInst() {
        Coffee blueMountain = new Coffee();
        objectMother.bear("blue_mountain", blueMountain);
        assertEquals(1l, blueMountain.id);
        assertEquals(new Price(1, Price.Currency.USD), blueMountain.price);
        assertEquals(Coffee.Bean.BlueMountain, blueMountain.bean);
        assertEquals("MyCoffee", blueMountain.seller);
    }

    @Test
    public void testBearSports() {
        Sports sports = objectMother.bear("soccer", Sports.class, new SportsMappingPostProcessor());
        assertSports(sports);
    }

    @Test
    public void testBearMultipleSports() {
        List<Sports> sportsList = objectMother.bear("soccer", Sports.class, 10, new SportsMappingPostProcessor());

        Assert.assertEquals(10, sportsList.size());
        for (Sports sports : sportsList) {
            assertSports(sports);
        }
    }

    protected void assertSports(Sports sports) {
        assertNotNull(sports.getName());
        assertTrue(sports.getTeams().size() == 4);

        for (Team team : sports.getTeams()) {
            assertNotNull(team.getName());
            assertNotNull(team.getDirector());
            assertNotNull(team.getCreatedAt());
            assertTrue(team.getStaff().size() == 2);
            for (Staff staff : team.getStaff()) {
                assertNotNull(staff.getPosition());
                assertNotNull(staff.getName());
                assertNotNull(staff.getGender());
                assertEquals(team, staff.getTeam());
            }
            assertTrue(team.getPlayers().length == 5);
            assertEquals(sports, team.getSports());
            for (Player player : team.getPlayers()) {
                assertNotNull(player.getNumber());
                assertNotNull(player.getName());
                assertNotNull(player.getGender());
                assertEquals(team, player.getTeam());
            }
        }
    }

    public static class SportsMappingPostProcessor extends PostProcessor<Sports> {

        @Override
        public void process(Sports bean, FixtureMap fixtureMap) {
            for(Team team : bean.getTeams()) {
                team.setSports(bean);
                for(Player player : team.getPlayers()) {
                    player.setTeam(team);
                }
                for(Staff stuff : team.getStaff()) {
                    stuff.setTeam(team);
                }
            }
        }
    }
}