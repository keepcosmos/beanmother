package io.beanmother.core.util;

import java.util.List;
import java.util.Map;

public class ObjectUtils {
    public static boolean isArrayOrListOrMap(Object value) {
        return value.getClass().isArray() || value.getClass().isInstance(List.class) ||value.getClass().isInstance(Map.class);
    }
}
