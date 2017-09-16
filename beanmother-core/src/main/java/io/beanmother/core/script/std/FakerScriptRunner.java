package io.beanmother.core.script.std;

import com.github.javafaker.Faker;
import io.beanmother.core.script.MethodReflectionEvalScriptRunner;

/**
 * A FakerScriptRunner is a ScriptRunner that wraps Faker library
 *
 * {@see <a herf="https://github.com/DiUS/java-faker">java-faker</a>}
 */
public class FakerScriptRunner extends MethodReflectionEvalScriptRunner {

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
