package io.beanmother.core.util;

import com.google.common.primitives.*;
import com.google.common.reflect.TypeToken;

import java.util.List;

/**
 * Utils about primitive type
 */
@SuppressWarnings("unchecked")
public class PrimitiveTypeUtils {

    /**
     * Convert primitive type to Wrapper type
     * @param primitiveTypeToken
     * @return Wrapper type
     */
    public static Class<?> toWrapper(final TypeToken<?> primitiveTypeToken) {
        return toWrapper((Class<?>) primitiveTypeToken.getType());
    }

    /**
     * Convert primitive type token to Wrapper type token
     * @param primitiveTypeToken
     * @return Wrapper type
     */
    public static TypeToken<?> toWrapperTypeToken(final TypeToken<?> primitiveTypeToken) {
        return TypeToken.of(toWrapper(primitiveTypeToken));
    }

    /**
     * Convert primitive type to Wrapper type
     * @param primitiveType
     * @return Wrapper type
     */
    public static Class<?> toWrapper(final Class<?> primitiveType) {
        if (boolean.class.equals(primitiveType)) {
            return Boolean.class;
        } else if (float.class.equals(primitiveType)) {
            return Float.class;
        } else if (long.class.equals(primitiveType)) {
            return Long.class;
        } else if (int.class.equals(primitiveType)) {
            return Integer.class;
        } else if (short.class.equals(primitiveType)) {
            return Short.class;
        } else if (byte.class.equals(primitiveType)) {
            return Byte.class;
        } else if (double.class.equals(primitiveType)) {
            return Double.class;
        } else if (char.class.equals(primitiveType)) {
            return Character.class;
        } else {
            throw new IllegalArgumentException(primitiveType.getName() + " is not supported primitive type");
        }
    }

    /**
     * Convert primitive array to Wrapper type list
     * @param wrapperList
     * @param primitiveType
     * @return
     */
    public static Object toWrapperListToPrimitiveArray(final List wrapperList, Class<?> primitiveType) {
        if (primitiveType.isArray()) {
            primitiveType = primitiveType.getComponentType();
        }

        if (boolean.class.equals(primitiveType)) {
            return Booleans.toArray(wrapperList);
        } else if (float.class.equals(primitiveType)) {
            return Floats.toArray(wrapperList);
        } else if (long.class.equals(primitiveType)) {
            return Longs.toArray(wrapperList);
        } else if (int.class.equals(primitiveType)) {
            return Ints.toArray(wrapperList);
        } else if (short.class.equals(primitiveType)) {
            return Shorts.toArray(wrapperList);
        } else if (byte.class.equals(primitiveType)) {
            return Bytes.toArray(wrapperList);
        } else if (double.class.equals(primitiveType)) {
            return Doubles.toArray(wrapperList);
        } else if (char.class.equals(primitiveType)) {
            return Chars.toArray(wrapperList);
        } else {
            throw new IllegalArgumentException(primitiveType.getName() + " is not supported primitive type");
        }
    }
}
