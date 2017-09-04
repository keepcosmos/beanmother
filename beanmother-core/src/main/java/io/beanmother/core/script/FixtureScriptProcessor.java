package io.beanmother.core.script;

import io.beanmother.core.fixture.FixtureValue;

public interface FixtureScriptProcessor {
    Object process(FixtureValue fixtureValue);
}
