package io.beanmother.core.script;

import io.beanmother.core.script.std.FakerScriptRunner;
import io.beanmother.core.script.std.SequenceScriptRunner;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A default implementation of a {@link ScriptHandler}
 */
public class DefaultScriptHandler implements ScriptHandler {

    private Set<ScriptRunner> scriptRunners = new HashSet<>();

    public DefaultScriptHandler() {
        scriptRunners.add(new FakerScriptRunner());
        scriptRunners.add(new SequenceScriptRunner());
    }

    @Override
    public Object runScript(ScriptFragment scriptFragment) {
        ScriptRunner process = get(scriptFragment);
        if (process == null) {
            throw new IllegalArgumentException("can not find ScriptRunner for " + scriptFragment.toScriptString());
        }
        return process.run(scriptFragment);
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
