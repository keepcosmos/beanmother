package io.beanmother.core.script.std;

import io.beanmother.core.script.ScriptRunner;
import io.beanmother.core.script.ScriptRunnerModule;

import java.util.HashSet;
import java.util.Set;

/**
 * A StandardScriptRunnerModule is collection of default {@link ScriptRunner}s.
 */
public class StandardScriptRunnerModule implements ScriptRunnerModule {

    private static final Set<ScriptRunner> standardScriptRunners;

    static {
        standardScriptRunners = new HashSet<>();
        standardScriptRunners.add(new FakerScriptRunner());
        standardScriptRunners.add(new SequenceScriptRunner());
    }

    @Override
    public Set<ScriptRunner> getScriptRunners() {
        return standardScriptRunners;
    }
}
