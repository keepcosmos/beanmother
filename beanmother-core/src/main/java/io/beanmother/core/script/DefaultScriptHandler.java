package io.beanmother.core.script;

import io.beanmother.core.fixture.FixtureValue;
import io.beanmother.core.script.std.FakerScriptProcessor;
import io.beanmother.core.script.std.SequenceScriptProcessor;

import java.util.HashSet;
import java.util.Set;

public class DefaultScriptHandler implements ScriptRunner {

    private Set<ScriptProcessor> scriptProcessors = new HashSet<>();

    public DefaultScriptHandler() {
        scriptProcessors.add(new FakerScriptProcessor());
        scriptProcessors.add(new SequenceScriptProcessor());
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

    @Override
    public void register(ScriptProcessor scriptProcessor) {
        this.scriptProcessors.add(scriptProcessor);
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
