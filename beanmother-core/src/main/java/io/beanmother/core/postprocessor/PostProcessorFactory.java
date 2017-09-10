package io.beanmother.core.postprocessor;

import com.google.common.reflect.TypeToken;
import edu.emory.mathcs.backport.java.util.Collections;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PostProcessorFactory {
    private Set<PostProcessor> postProcessors = new HashSet<>();

    public void register(PostProcessor<?> postProcessor) {
        postProcessors.add(postProcessor);
    }

    public <T> List<PostProcessor<T>> get(Class<T> targetType) {
        List<PostProcessor<T>> selected = new ArrayList<>();

        for (PostProcessor postProcessor : postProcessors) {
            Type type = postProcessor.getClass().getGenericInterfaces()[0];
            if (TypeToken.of(targetType).isSubtypeOf(TypeToken.of(type))) {
                selected.add(postProcessor);
            }
        }

        Collections.sort(selected);

        return selected;
    }

}
