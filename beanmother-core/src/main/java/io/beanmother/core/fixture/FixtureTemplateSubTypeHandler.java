package io.beanmother.core.fixture;

public abstract class FixtureTemplateSubTypeHandler {

    public void handle(FixtureTemplate fixtureTemplate) {
        if (fixtureTemplate instanceof FixtureMap) {
            handleIf((FixtureMap) fixtureTemplate);
        } else if (fixtureTemplate instanceof FixtureList) {
            handleIf((FixtureList) fixtureTemplate);
        } else if (fixtureTemplate instanceof FixtureValue) {
            handleIf((FixtureValue) fixtureTemplate);
        }
    }

    protected abstract void handleIf(FixtureMap fixtureMap);
    protected abstract void handleIf(FixtureList fixtureList);
    protected abstract void handleIf(FixtureValue fixtureValue);
}
