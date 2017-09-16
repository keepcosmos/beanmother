package io.beanmother.core.script.std;

import io.beanmother.core.script.ScriptFragment;
import io.beanmother.core.script.ScriptOperationException;
import io.beanmother.core.script.ScriptRunner;

import java.util.concurrent.atomic.AtomicLong;

/**
 * A SequenceScriptRunner returns sequential number (increase 1) when it runs.
 */
public class SequenceScriptRunner implements ScriptRunner {

    private final static String NAMESPACE = "sequence";

    private final static String NUMBER_SEQUENCE_METHOD_NAME = "number";

    private AtomicLong longSequence = new AtomicLong(0);

    @Override
    public Object run(ScriptFragment scriptFragment) {
        if (!canHandle(scriptFragment)) throw new ScriptOperationException(scriptFragment.getMethodName() + " is not support.");

        if (scriptFragment.getNext() == null
                || scriptFragment.getNext().getMethodName().equals(NUMBER_SEQUENCE_METHOD_NAME)) {
            return longSequence.addAndGet(1l);
        } else {
            throw new ScriptOperationException(scriptFragment.getMethodName() + " is not support.");
        }
    }

    @Override
    public boolean canHandle(ScriptFragment scriptFragment) {
        return scriptFragment.getMethodName().equals(NAMESPACE);
    }
}
