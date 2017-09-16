package io.beanmother.core.script;

import io.beanmother.core.common.FixtureValue;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link ScriptFragment}
 */
public class ScriptFragmentTest {

    @Test
    public void testCheckScriptFixtureValue() {
        assertFalse(ScriptFragment.isScript(new FixtureValue("test")));
        assertFalse(ScriptFragment.isScript(new FixtureValue(1)));
        assertFalse(ScriptFragment.isScript(new FixtureValue("{test}")));
        assertFalse(ScriptFragment.isScript(new FixtureValue("${test")));

        assertTrue(ScriptFragment.isScript(new FixtureValue("${test}")));
        assertTrue(ScriptFragment.isScript(new FixtureValue("${test.example}")));
        assertTrue(ScriptFragment.isScript(new FixtureValue("${test.example.abc()}")));
    }

    @Test
    public void testBuildScriptFragment() {
        ScriptFragment fragment = ScriptFragment.of(new FixtureValue("${test.example}"));
        assertEquals("test", fragment.getMethodName());
        assertFalse(fragment.hasArguments());
        assertEquals("example", fragment.getNext().getMethodName());
        assertFalse(fragment.getNext().hasArguments());
    }

    @Test
    public void testBuildArgumentsScriptFragment() {
        ScriptFragment fragment = ScriptFragment.of(new FixtureValue("${test('a', \"b\", 3).example.script()}"));
        assertEquals("test", fragment.getMethodName());
        assertTrue(fragment.hasArguments());
        assertEquals("a", fragment.getArguments().get(0));
        assertEquals("b", fragment.getArguments().get(1));
        assertEquals("3", fragment.getArguments().get(2));

        fragment = fragment.getNext();
        assertEquals("example", fragment.getMethodName());
        assertFalse(fragment.hasArguments());

        fragment = fragment.getNext();
        assertEquals("script", fragment.getMethodName());
        assertFalse(fragment.hasArguments());
    }

    @Test
    public void testGetAllScript() {
        ScriptFragment fragment = ScriptFragment.of(new FixtureValue("${test('a', 'b', 'c')}"));
        assertEquals("test('a','b','c')", fragment.toScriptString());

        fragment = ScriptFragment.of(new FixtureValue("${test.example.script}"));
        assertEquals("test.example.script", fragment.toScriptString());

        fragment = ScriptFragment.of(new FixtureValue("${test(1, '2', '3').example.script('a')}"));
        assertEquals("test('1','2','3').example.script('a')", fragment.toScriptString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailParse() {
        ScriptFragment.of(new FixtureValue(1));
    }
}