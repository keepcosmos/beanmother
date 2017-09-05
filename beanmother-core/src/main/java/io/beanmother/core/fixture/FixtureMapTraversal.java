package io.beanmother.core.fixture;

import java.util.Map;

/**
 * It traverses(DFS) FixtureMap's all edge elements.
 */
public class FixtureMapTraversal {

    /**
     * The interface that runs when visit edges.
     */
    interface Processor {
        /**
         * Run when visiting a edge.
         * @param edge
         */
        void visit(FixtureValue edge);
    }


    /**
     * Traverse each edges({@link FixtureValue}) and run {@link Processor} when it meets a edge.
     * @param fixtureMap
     * @param processor
     */
    public static void traverse(FixtureMap fixtureMap, final Processor processor) {
        for (Map.Entry<String, FixtureTemplate> entry : fixtureMap.entrySet()) {
            handleSubType(entry.getValue(), processor);
        }
    }

    private static void traverse(FixtureList fixtureList, final Processor processor) {
        for (FixtureTemplate fixtureTemplate : fixtureList) {
            handleSubType(fixtureTemplate, processor);
        }
    }

    private static void handleSubType(FixtureTemplate fixtureTemplate, final Processor processor) {
        new FixtureTemplateSubTypeHandler() {
            @Override
            protected void handleIf(FixtureMap fixtureMap) {
                traverse(fixtureMap, processor);
            }

            @Override
            protected void handleIf(FixtureList fixtureList) {
                traverse(fixtureList, processor);
            }

            @Override
            protected void handleIf(FixtureValue fixtureValue) {
                processor.visit(fixtureValue);
            }
        }.handle(fixtureTemplate);
    }
}
