package io.beanmother.core.script;

import com.google.common.reflect.TypeToken;
import io.beanmother.core.converter.Converter;
import io.beanmother.core.converter.ConverterFactory;

import javax.naming.OperationNotSupportedException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class MethodReflectionEvalScriptRunner implements ScriptRunner {

    private final static ConverterFactory converterFactory = new ConverterFactory();

    public abstract Object getTargetObject();

    public abstract String getScriptNamespace();

    @Override
    public Object run(ScriptFragment scriptFragment) {
        ScriptFragment mainScript = scriptFragment.getNext();
        try {
            return callScriptRecursively(getTargetObject(), mainScript);
        } catch (Exception e) {
            throw new ScriptOperationException("Fail to operate " + scriptFragment.toScriptString(), e);
        }
    }

    @Override
    public boolean canHandle(ScriptFragment scriptFragment) {
        return (scriptFragment.getNext() != null) && scriptFragment.getMethodName().equals(getScriptNamespace());
    }

    private Object callScriptRecursively(Object targetObj, ScriptFragment scriptFragment) throws OperationNotSupportedException {
        if (scriptFragment == null) return targetObj;

        Method[] methods = targetObj.getClass().getMethods();

        List<Method> targetMethods = new ArrayList<>();
        for (Method method : methods) {
            if (method.getName().equals(scriptFragment.getMethodName())) {
                // I've no idea how to find method with only string script arguments.
                // Just try with arguments count.
                if (method.getParameterTypes().length == scriptFragment.getArguments().size()) {
                    targetMethods.add(method);
                }
            }
        }

        for (Method targetMethod : targetMethods) {
            Class<?>[] types = targetMethod.getParameterTypes();
            List<Object> arguments = new ArrayList<>();
            try {
                for (int i = 0 ; i < types.length ; i++) {
                    arguments.add(convert(scriptFragment.getArguments().get(i), TypeToken.of(types[i])));
                }
                Object obj = targetMethod.invoke(targetObj, arguments.toArray());
                return callScriptRecursively(obj, scriptFragment.getNext());
            } catch (Exception e) {
                continue;
            }
        }
        throw new OperationNotSupportedException("can not find " + scriptFragment.getMethodName() + " of " + targetObj.getClass());
    }

    private Object convert(Object source, TypeToken<?> typeToken) {
        Converter converter = converterFactory.get(source, typeToken);
        if (converter == null) throw new IllegalArgumentException("can not convert " + source + " to the instance of " + typeToken.getRawType());
        return converter.convert(source, typeToken);
    }
}
