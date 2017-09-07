package io.beanmother.core.script;

import io.beanmother.core.fixture.FixtureValue;

/**
 * Abstract test for implementations of {@link ScriptProcessor}
 */
public abstract class ScriptProcessorTest {
    protected <T> T process(ScriptProcessor processor, String script, Class<T> type) {
        FixtureValue fixture = new FixtureValue("${" +script + "}");
        return (T) processor.process(ScriptFragment.of(fixture));
    }

    protected Object process(ScriptProcessor processor, String script) {
        return process(processor, script, Object.class);
    }
}
