package io.beanmother.core.script;

public interface ScriptRunner {
    Object run(ScriptFragment scriptFragment);
    boolean canHandle(ScriptFragment scriptFragment);
}
