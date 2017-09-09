package io.beanmother.core.script;

import com.github.javafaker.Faker;
import io.beanmother.core.fixture.FixtureValue;
import io.beanmother.core.script.std.FakerScriptProcessor;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link FakerScriptProcessor}
 */
public class FakerScriptProcessorTest extends ScriptProcessorTest {

    FakerScriptProcessor processor = new FakerScriptProcessor();
    Faker faker = new Faker();

    @Test
    public void testCanHandle() {
        ScriptFragment fragment = null;

        fragment = ScriptFragment.of(new FixtureValue("${faker}"));
        assertFalse(processor.canHandle(fragment));

        fragment = ScriptFragment.of(new FixtureValue("${faker.name.fullName}"));
        assertTrue(processor.canHandle(fragment));

        fragment = ScriptFragment.of(new FixtureValue("${real.name.fullName}"));
        assertFalse(processor.canHandle(fragment));
    }

    @Test
    public void testName() {
        assertTrue(process(processor, "faker.name.fullName", String.class).length() > 0);
        assertTrue(process(processor, "faker.name.firstName", String.class).length() > 0);
        assertTrue(process(processor, "faker.name.title", String.class).length() > 0);
    }

    @Test
    public void testLorem() {
        assertTrue(process(processor, "faker.lorem.paragraph", String.class).length() > 1);
        assertTrue(process(processor, "faker.lorem.sentence(2)", String.class).length() > 1);
    }

    @Test
    public void testNumber() {
        Integer num = process(processor, "faker.number.numberBetween(1, 3)", Integer.class);
        assertTrue(num.intValue() >= 1 && num.intValue() <= 3);

        assertTrue(process(processor, "faker.number.randomDigit") instanceof Integer);
        assertTrue(process(processor, "faker.number.randomNumber") instanceof Long);
    }

    @Test
    public void testDate() {
        Date date = process(processor, "faker.date.between('2017-01-03', '2017-01-31')", Date.class);
        assertTrue(date.getTime() > 1483282800000l && date.getTime() < 1485874800000l);

        date = process(processor, "faker.date.future(1, 'hours')", Date.class);
        assertTrue(date.compareTo(new Date()) == 1);
        date = process(processor, "faker.date.future(1, 'days')", Date.class);
        assertTrue(date.compareTo(new Date()) == 1);

        date = process(processor, "faker.date.past(1, 'hours')", Date.class);
        assertTrue(date.compareTo(new Date()) == -1);
        date = process(processor, "faker.date.past(1, 'days')", Date.class);
        assertTrue(date.compareTo(new Date()) == -1);
    }
}