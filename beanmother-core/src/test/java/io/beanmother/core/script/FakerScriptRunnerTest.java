package io.beanmother.core.script;

import com.github.javafaker.Faker;
import io.beanmother.core.common.FixtureValue;
import io.beanmother.core.script.std.FakerScriptRunner;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link FakerScriptRunner}
 */
public class FakerScriptRunnerTest extends ScriptProcessorTest {

    FakerScriptRunner scriptRunner = new FakerScriptRunner();
    Faker faker = new Faker();

    @Test
    public void testCanHandle() {
        ScriptFragment fragment = null;

        fragment = ScriptFragment.of(new FixtureValue("${faker}"));
        assertFalse(scriptRunner.canHandle(fragment));

        fragment = ScriptFragment.of(new FixtureValue("${faker.name.fullName}"));
        assertTrue(scriptRunner.canHandle(fragment));

        fragment = ScriptFragment.of(new FixtureValue("${real.name.fullName}"));
        assertFalse(scriptRunner.canHandle(fragment));
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
        assertTrue(date.compareTo(new Date()) == 1);
        date = run(scriptRunner, "faker.date.future(1, 'days')", Date.class);
        assertTrue(date.compareTo(new Date()) == 1);

        date = run(scriptRunner, "faker.date.past(1, 'hours')", Date.class);
        assertTrue(date.compareTo(new Date()) == -1);
        date = run(scriptRunner, "faker.date.past(1, 'days')", Date.class);
        assertTrue(date.compareTo(new Date()) == -1);
    }
}