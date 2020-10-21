package io.beanmother.core.script;

import com.github.javafaker.Faker;
import io.beanmother.core.script.std.FakerScriptRunner;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Test for {@link FakerScriptRunner}
 */
public class FakerScriptRunnerTest extends ScriptProcessorTest {

    FakerScriptRunner scriptRunner = new FakerScriptRunner();
    Faker faker = new Faker();

    @Test
    public void testCanHandle() {
        assertFalse(scriptRunner.canHandle(ScriptFragment.of("faker")));
        assertTrue(scriptRunner.canHandle(ScriptFragment.of("faker.name.fullName")));
        assertFalse(scriptRunner.canHandle(ScriptFragment.of("real.name.fullName")));
    }

    @Test
    public void testName() {
        assertTrue(run(scriptRunner, "faker.name.fullName", String.class).length() > 0);
        assertTrue(run(scriptRunner, "faker.name.firstName", String.class).length() > 0);
        assertTrue(run(scriptRunner, "faker.name.title", String.class).length() > 0);
    }

    @Test
    public void testLorem() {
        assertTrue(run(scriptRunner, "faker.lorem.paragraph", String.class).length() > 1);
        assertTrue(run(scriptRunner, "faker.lorem.sentence(2)", String.class).length() > 1);
    }

    @Test
    public void testNumber() {
        int num = ((Number) run(scriptRunner, "faker.number.numberBetween(1, 3)")).intValue();
        assertTrue(num >= 1 && num <= 3);

        assertTrue(run(scriptRunner, "faker.number.randomDigit") instanceof Integer);
        assertTrue(run(scriptRunner, "faker.number.randomNumber") instanceof Long);
    }

    @Test
    public void testDate() {
        Date date = run(scriptRunner, "faker.date.between('2017-01-03', '2017-01-31')", Date.class);
        assertTrue(date.getTime() > 1483282800000l && date.getTime() < 1485874800000l);

        date = run(scriptRunner, "faker.date.future(1, 'hours')", Date.class);
        assertEquals(date.compareTo(new Date()), 1);
        date = run(scriptRunner, "faker.date.future(1, 'days')", Date.class);
        assertEquals(date.compareTo(new Date()), 1);

        date = run(scriptRunner, "faker.date.past(1, 'hours')", Date.class);
        assertEquals(-1, date.compareTo(new Date()));
        date = run(scriptRunner, "faker.date.past(1, 'days')", Date.class);
        assertEquals(-1, date.compareTo(new Date()));
    }

    @Test
    public void testOptions() {
        String selected = run(scriptRunner, "faker.options('MALE', 'FEMALE')", String.class);
        Assert.assertTrue(selected.equals("MALE") || selected.equals("FEMALE"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOptionsRaiseError() {
        String selected = run(scriptRunner, "faker.options", String.class);
    }

    @Test(expected = ScriptOperationException.class)
    public void testRaiseException() {
        scriptRunner.run(ScriptFragment.of("faker.unkown.fail"));
    }
}