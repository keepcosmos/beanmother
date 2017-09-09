package io.beanmother.core.script.std;

import com.github.javafaker.Faker;
import io.beanmother.core.script.MethodReflectionEvalScriptProcessor;

public class FakerScriptProcessor extends MethodReflectionEvalScriptProcessor {

    private final static String SCRIPT_NAMESPACE = "faker";

    private final static Faker faker = new Faker();

    @Override
    public Object getTargetObject() {
        return faker;
    }

    @Override
    public String getScriptNamespace() {
        return SCRIPT_NAMESPACE;
    }
}
