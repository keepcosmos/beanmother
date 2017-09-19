package io.beanmother.core.script;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Test for {@link DefaultScriptHandler}
 */
public class DefaultScriptHandlerTest {

    /**
     * Test ScriptRunner for unit tests.
     */
    public static class TestScriptRunner implements ScriptRunner {
        @Override
        public Object run(ScriptFragment scriptFragment) {
            return 1;
        }

        @Override
        public boolean canHandle(ScriptFragment scriptFragment) {
            return scriptFragment.getMethodName().equals("fortest");
        }
    }

    /**
     * Test ScriptRunnerModule for unit tests.
     */
    public static class TestScriptRunnerModule implements ScriptRunnerModule {
        private static Set<ScriptRunner> scriptRunners;

        static {
            scriptRunners = new HashSet<>();
            scriptRunners.add(new TestScriptRunner());
        }

        @Override
        public Set<ScriptRunner> getScriptRunners() {
            return scriptRunners;
        }
    }

    DefaultScriptHandler scriptHandler = null;

    @Before
    public void setup() {
        scriptHandler = new DefaultScriptHandler();
    }

    @Test
    public void testRegister() {
        TestScriptRunner testScriptRunner = new TestScriptRunner();
        scriptHandler.register(testScriptRunner);

        ScriptRunner actual = scriptHandler.get(ScriptFragment.of("fortest"));

        assertEquals(testScriptRunner, actual);

        assertNull(scriptHandler.get(ScriptFragment.of("nothing")));
    }

    @Test
    public void testRegisterScriptRunnerModule() {
        TestScriptRunnerModule testModule = new TestScriptRunnerModule();
        scriptHandler.register(testModule);

        assertNotNull(scriptHandler.get(ScriptFragment.of("fortest")));
    }

    @Test
    public void testRunScript() {
        scriptHandler.register(new TestScriptRunner());
        assertEquals(1, scriptHandler.runScript(ScriptFragment.of("fortest")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRaiseError() {
        scriptHandler.runScript(ScriptFragment.of("fail"));
    }
}