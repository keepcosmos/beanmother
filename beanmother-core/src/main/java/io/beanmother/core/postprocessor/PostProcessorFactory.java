package io.beanmother.core.postprocessor;

import com.google.common.reflect.TypeToken;
import edu.emory.mathcs.backport.java.util.Collections;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A PostProcessorFactory is a factory for PostProcessor.
 */
public class PostProcessorFactory {
    private Set<PostProcessor> postProcessors = new HashSet<>();

    /**
     * Register the PostProcessor
     * @param postProcessor the PostProcessor.
     */
    public void register(PostProcessor postProcessor) {
        postProcessors.add(postProcessor);
    }

    /**
     * Get a sorted PostProcessors by generic type
     * @param targetType the generic type of a registered PostProcessor.
     * @param <T> the type.
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<PostProcessor<T>> get(Class<T> targetType) {
        List<PostProcessor<T>> selected = new ArrayList<>();

        for (PostProcessor postProcessor : postProcessors) {
            Type type = postProcessor.getTargetClass();
            if (TypeToken.of(targetType).isSubtypeOf(TypeToken.of(type))) {
                selected.add(postProcessor);
            }
        }

        Collections.sort(selected);

        return selected;
    }

}
