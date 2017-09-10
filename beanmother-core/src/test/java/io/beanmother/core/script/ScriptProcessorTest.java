package io.beanmother.core.script;

import io.beanmother.core.common.FixtureValue;

/**
 * Abstract test for implementations of {@link ScriptRunner}
 */
public abstract class ScriptProcessorTest {
    protected <T> T run(ScriptRunner runner, String script, Class<T> type) {
        FixtureValue fixture = new FixtureValue("${" +script + "}");
        return (T) runner.run(ScriptFragment.of(fixture));
    }

    protected Object run(ScriptRunner runner, String script) {
        return run(runner, script, Object.class);
    }
}
