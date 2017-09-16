package io.beanmother.core.script.std;

import io.beanmother.core.script.ScriptRunner;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Test for {@link StandardScriptRunnerModule}
 */
public class StandardScriptRunnerModuleTest {

    StandardScriptRunnerModule module = new StandardScriptRunnerModule();

    @Test
    public void testRegiesteredScriptRunners() {
        Set<Class> registered = new HashSet<>();
        for (ScriptRunner scriptRunner : module.getScriptRunners()) {
            registered.add(scriptRunner.getClass());
        }

        assertTrue(registered.contains(FakerScriptRunner.class));
        assertTrue(registered.contains(SequenceScriptRunner.class));
    }
}