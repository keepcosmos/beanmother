package io.beanmother.core.script;

public interface ScriptProcessor {
    Object process(ScriptFragment fixtureValue);
    boolean canHandle(ScriptFragment fixtureValue);
}
