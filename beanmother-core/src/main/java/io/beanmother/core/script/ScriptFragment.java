package io.beanmother.core.script;

import io.beanmother.core.fixture.FixtureValue;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptFragment {

    private final static Pattern FIXTURE_VALUE_SCRIPT_PATTERN = Pattern.compile("(?<=\\$\\{)(.+?)(?=})");

    private final static Pattern ARGUMENTS_PATTERN = Pattern.compile("(?<=\\()(.*?)(?=\\))");

    private final static String FRAGMENT_CHAIN_CHAR = "\\.";

    private String methodName;

    private List<String> arguments = new ArrayList<>();

    private ScriptFragment next;

    public static ScriptFragment of(FixtureValue fixtureValue) {
        if (fixtureValue.getValue() instanceof String) {
            Matcher matcher = FIXTURE_VALUE_SCRIPT_PATTERN.matcher((CharSequence) fixtureValue.getValue());
            if (matcher.find()) {
                String[] scripts = matcher.group(0).split(FRAGMENT_CHAIN_CHAR);

                ScriptFragment scriptFragment = null;
                for (String script : scripts) {
                    if (scriptFragment == null) {
                        scriptFragment = of(script);
                    } else {
                        scriptFragment.appendToTail(of(script));
                    }
                }
                return scriptFragment;
            }
        }
        throw new IllegalArgumentException(fixtureValue.toString() + "is not a script");
    }

    public static ScriptFragment of(String script) {
        Matcher argumentMatcher = ARGUMENTS_PATTERN.matcher(script);
        if (argumentMatcher.find()) {
            String[] arguments = argumentMatcher.group(0).split(",");
            script = script.substring(0, script.indexOf("("));

            if (arguments.length == 1 && arguments[0].trim().length() == 0) {
                return new ScriptFragment(script);
            } else {
                return new ScriptFragment(script, arguments);
            }
        } else {
            return new ScriptFragment(script);
        }
    }

    public static boolean isScript(FixtureValue fixtureValue) {
        return (fixtureValue.getValue() instanceof String)
                && FIXTURE_VALUE_SCRIPT_PATTERN.matcher((CharSequence) fixtureValue.getValue()).find();
    }

    public ScriptFragment(String methodName) {
        this.methodName = methodName.trim();
    }

    public ScriptFragment(String methodName, String ... arguments) {
        this(methodName);
        for (String argument :arguments) {
            this.arguments.add(argument.trim().replaceAll("\"", "").replaceAll("\'", ""));
        }
    }

    public String getMethodName() {
        return methodName;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public boolean hasArguments() {
        return (arguments != null) && !arguments.isEmpty();
    }

    public ScriptFragment getNext() {
        return next;
    }

    public void appendToTail(ScriptFragment scriptFragment) {
        if (next == null) {
            next = scriptFragment;
        } else {
            next.appendToTail(scriptFragment);
        }
    }
}
