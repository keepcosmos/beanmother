package io.beanmother.core.script;

import com.github.javafaker.Faker;

public class FakerScriptProcessor extends MethodReflectionScriptProcessor {

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
