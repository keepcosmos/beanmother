package io.beanmother.core.script;

import io.beanmother.core.common.FixtureValue;
import io.beanmother.core.script.std.FakerScriptRunner;
import io.beanmother.core.script.std.SequenceScriptRunner;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DefaultScriptHandler implements ScriptHandler {

    private Set<ScriptRunner> scriptRunners = new HashSet<>();

    public DefaultScriptHandler() {
        scriptRunners.add(new FakerScriptRunner());
        scriptRunners.add(new SequenceScriptRunner());
    }

    @Override
    public void runScript(FixtureValue fixtureValue) {
        ScriptFragment fragment = ScriptFragment.of(fixtureValue);
        ScriptRunner process = get(fragment);
        if (process == null) {
            throw new RuntimeException("can not find ScriptRunner for " + fixtureValue);
        }
        fixtureValue.setValue(process.run(fragment));
    }

    @Override
    public void register(ScriptRunnerModule scriptRunnerModule) {
        Iterator<ScriptRunner> elements = scriptRunnerModule.getScriptRunners().iterator();

        while (elements.hasNext()) {
            register(elements.next());
        }
    }

    @Override
    public void register(ScriptRunner scriptRunner) {
        this.scriptRunners.add(scriptRunner);
    }

    public ScriptRunner get(ScriptFragment scriptFragment) {
        for (ScriptRunner processor : scriptRunners) {
            if (processor.canHandle(scriptFragment)) {
                return processor;
            }
        }
        return null;
    }
}
