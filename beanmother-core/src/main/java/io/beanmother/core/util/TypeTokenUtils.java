package io.beanmother.core.util;

import com.google.common.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Util class for {@link com.google.common.reflect.TypeToken}
 */
public abstract class TypeTokenUtils {

    /**
     * Extract TypeToken of generic type or Array component type.
     * @param typeToken Target TypeToken
     * @return generic TypeToken if typeToken is array or component TypeToken if typeToken is Array
     */
    public static TypeToken<?> extractElementTypeToken(TypeToken<?> typeToken) {
        if (typeToken.isSubtypeOf(Collection.class)) {
            List<TypeToken<?>> genericTypeTokens = TypeTokenUtils.extractGenericTypeTokens(typeToken);
            if (genericTypeTokens.isEmpty()) {
                return TypeToken.of(Object.class);
            } else {
                return genericTypeTokens.get(0);
            }
        } else if (typeToken.isArray()) {
            return typeToken.getComponentType();
        } else {
            throw new IllegalArgumentException("typeToken must be from List or array");
        }
    }

    /**
     * Extract TypeTokens of Generics.
     * @param typeToken Target TypeToken
     * @return TypeTokens of generic types in given type.
     */
    public static List<TypeToken<?>> extractGenericTypeTokens(TypeToken<?> typeToken) {
        List<TypeToken<?>> typeTokens = new ArrayList<>();
        try {
            Type[] types = ((ParameterizedType) typeToken.getType()).getActualTypeArguments();
            for (Type type : types) {
                typeTokens.add(TypeToken.of(type));
            }
        } catch (ClassCastException e) {
            // Do nothing
        }
        return typeTokens;
    }
}
