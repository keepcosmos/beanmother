package io.beanmother.core.script;

import java.util.Set;

/**
 * The root interface for build module class
 *
 * A implementation of this interface is for grouping of {@link ScriptRunner}s.
 */
public interface ScriptRunnerModule {

    /**
     * Get registered {@link ScriptRunner}s.
     * @return Set of ScriptRunner
     */
    Set<ScriptRunner> getScriptRunners();
}
