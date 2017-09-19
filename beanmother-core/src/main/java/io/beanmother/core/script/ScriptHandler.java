package io.beanmother.core.script;

/**
 * The root interface for running script fragments.
 *
 * It can register {@link ScriptRunner}, find suitable {@link ScriptRunner} by fixtureValue that has script and run it.
 */
public interface ScriptHandler {

    /**
     * Run script and return the result.
     * @param scriptFragment
     */
    Object runScript(ScriptFragment scriptFragment);

    /**
     * Register a ScriptRunnerModule
     * @param scriptRunnerModule the scriptRunnerModule
     */
    void register(ScriptRunnerModule scriptRunnerModule);

    /**
     * Register a ScriptRunner
     * @param scriptRunner the scriptRunner
     */
    void register(ScriptRunner scriptRunner);
}
