package io.beanmother.core.script;

import io.beanmother.core.common.FixtureValue;

public interface ScriptHandler {
    void runScript(FixtureValue fixtureValue);
    void register(ScriptRunnerModule scriptRunnerModule);
    void register(ScriptRunner scriptRunner);
}
