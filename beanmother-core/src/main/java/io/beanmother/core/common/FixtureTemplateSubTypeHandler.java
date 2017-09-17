package io.beanmother.core.common;

/**
 * FixtureTemplate has {@link FixtureMap}, {@link FixtureList} and {@link FixtureValue} as a subtype.
 *
 * For parsing FixtureTemplate for many ways, Checking type (like `instanceof`) is used so often. But, It has risks about missing some subtype of FixtureTemplate.
 *
 * So, for avoiding conditional(if/else things) logic, using abstract class is recommended.
 *
 */
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
