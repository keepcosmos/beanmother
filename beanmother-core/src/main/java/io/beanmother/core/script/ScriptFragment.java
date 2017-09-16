package io.beanmother.core.script;

import io.beanmother.core.common.FixtureValue;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The fragment from a script string.
 *
 * Each script fragment has method name, arguments and trailing script fragment(or not), like a linked-list structure.
 */
public class ScriptFragment {

    /**
     * Script pattern of the value inside of {@link FixtureValue}
     */
    private final static Pattern FIXTURE_VALUE_SCRIPT_PATTERN = Pattern.compile("(?<=\\$\\{)(.+?)(?=})");

    /**
     * Script Arguments pattern
     */
    private final static Pattern ARGUMENTS_PATTERN = Pattern.compile("(?<=\\()(.*?)(?=\\))");


    private final static String FRAGMENT_DELIM = "\\.";

    /**
     * Script method name
     */
    private String methodName;

    /**
     * Arguments
     */
    private List<String> arguments = new ArrayList<>();

    /**
     * Trailing script fragment
     */
    private ScriptFragment next;

    /**
     * Parse a FixtueValue to ScriptFragments
     * @param fixtureValue the FixtureValue
     * @return
     */
    public static ScriptFragment of(FixtureValue fixtureValue) {
        if (fixtureValue.getValue() instanceof String) {
            Matcher matcher = FIXTURE_VALUE_SCRIPT_PATTERN.matcher((CharSequence) fixtureValue.getValue());
            if (matcher.find()) {
                String script = matcher.group(0);
                return of(script);
            }
        }
        throw new IllegalArgumentException(fixtureValue.toString() + " is not a script");
    }

    /**
     * Parse a String to ScriptFragments
     * @param script the string of script
     * @return
     */
    public static ScriptFragment of(String script) {
        String[] fragmentStrings = script.split(FRAGMENT_DELIM);

        ScriptFragment scriptFragment = null;
        for (String fragmentString : fragmentStrings) {
            if (scriptFragment == null) {
                scriptFragment = build(fragmentString);
            } else {
                scriptFragment.appendToTail(build(fragmentString));
            }
        }
        return scriptFragment;
    }

    private static ScriptFragment build(String script) {
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

    /**
     * Create a ScriptFragment
     * @param methodName
     */
    public ScriptFragment(String methodName) {
        this.methodName = methodName.trim();
    }

    /**
     * Create a ScriptFragment.
     * @param methodName
     * @param arguments
     */
    public ScriptFragment(String methodName, String ... arguments) {
        this(methodName);
        for (String argument :arguments) {
            this.arguments.add(argument.trim().replaceAll("\"", "").replaceAll("\'", ""));
        }
    }

    /**
     * Get script method name.
     * @return
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Get script arguments.
     * @return
     */
    public List<String> getArguments() {
        return arguments;
    }

    /**
     * Check existence of arguments.
     * @return
     */
    public boolean hasArguments() {
        return (arguments != null) && !arguments.isEmpty();
    }

    /**
     * Get next(trailing) ScriptFragment.
     * @return
     */
    public ScriptFragment getNext() {
        return next;
    }

    /**
     * Append ScriptFragment to tail.
     * @param scriptFragment
     */
    public void appendToTail(ScriptFragment scriptFragment) {
        if (next == null) {
            next = scriptFragment;
        } else {
            next.appendToTail(scriptFragment);
        }
    }

    /**
     * Get string of all trailing script.
     * @return
     */
    public String toScriptString() {
        StringBuilder builder = new StringBuilder();
        builder.append(methodName);

        if (arguments != null && !arguments.isEmpty()) {
            builder.append("(");
            for (int i = 0 ; i < arguments.size() ; i++) {
                builder.append("'" + arguments.get(i) + "'");
                if (i < arguments.size() - 1) {
                    builder.append(",");
                }
            }
            builder.append(")");
        }

        if (getNext() != null) {
            builder.append(".").append(getNext().toScriptString());
        }

        return builder.toString();
    }
}
