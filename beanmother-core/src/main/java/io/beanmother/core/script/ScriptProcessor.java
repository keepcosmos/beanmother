package io.beanmother.core.script;

public interface ScriptProcessor {
    Object process(ScriptFragment scriptFragment);
    boolean canHandle(ScriptFragment scriptFragment);
}
