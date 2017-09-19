package io.beanmother.core.script;

/**
 * Abstract test for implementations of {@link ScriptRunner}
 */
@SuppressWarnings("unchecked")
public abstract class ScriptProcessorTest {
    protected <T> T run(ScriptRunner runner, String script, Class<T> type) {
        return (T) runner.run(ScriptFragment.of(script));
    }

    protected Object run(ScriptRunner runner, String script) {
        return run(runner, script, Object.class);
    }
}
