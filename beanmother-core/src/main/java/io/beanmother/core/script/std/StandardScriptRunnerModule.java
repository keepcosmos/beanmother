package io.beanmother.core.script.std;

import io.beanmother.core.script.ScriptRunner;
import io.beanmother.core.script.ScriptRunnerModule;

import java.util.HashSet;
import java.util.Set;

public class StandardScriptRunnerModule implements ScriptRunnerModule {

    private final static Set<ScriptRunner> standardScriptRunners;

    static {
        standardScriptRunners = new HashSet<>();
        standardScriptRunners.add(new FakerScriptRunner());
        standardScriptRunners.add(new SequenceScriptRunner());
    }

    @Override
    public Iterable<ScriptRunner> getScriptRunners() {
        return standardScriptRunners;
    }
}
