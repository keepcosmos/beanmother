package io.beanmother.core.script.std;

import io.beanmother.core.script.ScriptFragment;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for {@link SequenceScriptRunner}
 */
public class SequenceScriptRunnerTest {

    SequenceScriptRunner scriptRunner = new SequenceScriptRunner();

    @Test
    public void testCanHandle() throws Exception {
        assertTrue(scriptRunner.canHandle(ScriptFragment.of("sequence.number")));

        assertFalse(scriptRunner.canHandle(ScriptFragment.of("faker.number")));
    }

    @Test
    public void run() throws Exception {
        assertEquals(1l, scriptRunner.run(ScriptFragment.of("sequence.number")));
        assertEquals(2l, scriptRunner.run(ScriptFragment.of("sequence.number")));
        assertEquals(3l, scriptRunner.run(ScriptFragment.of("sequence.number")));
        assertEquals(4l, scriptRunner.run(ScriptFragment.of("sequence.number")));
        assertEquals(5l, scriptRunner.run(ScriptFragment.of("sequence.number")));
    }

}