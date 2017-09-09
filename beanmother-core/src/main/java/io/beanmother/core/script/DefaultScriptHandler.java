package io.beanmother.core.script;

import io.beanmother.core.fixture.FixtureValue;
import io.beanmother.core.script.std.FakerScriptProcessor;

import java.util.HashSet;
import java.util.Set;

public class DefaultScriptHandler implements ScriptRunner {

    private Set<ScriptProcessor> scriptProcessors = new HashSet<>();

    public DefaultScriptHandler() {
        scriptProcessors.add(new FakerScriptProcessor());
    }

    @Override
    public void runScript(FixtureValue fixtureValue) {
        ScriptFragment fragment = ScriptFragment.of(fixtureValue);
        ScriptProcessor process = get(fragment);
        if (process == null) {
            throw new RuntimeException("can not find ScriptProcessor for " + fixtureValue);
        }
        fixtureValue.setValue(process.process(fragment));
    }

    public void regiester(ScriptProcessor processor) {
        this.scriptProcessors.add(processor);
    }

    public ScriptProcessor get(ScriptFragment scriptFragment) {
        for (ScriptProcessor processor : scriptProcessors) {
            if (processor.canHandle(scriptFragment)) {
                return processor;
            }
        }
        return null;
    }
}
