package io.beanmother.core.util;

/**
 * Util class about Class
 */
public abstract class ClassUtils {
    /**
     * Return the default ClassLoader
     * @return classloader
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } finally {
            if (cl == null) {
                cl = ClassUtils.class.getClassLoader();
            }
            if (cl == null) {
                cl = ClassLoader.getSystemClassLoader();
            }
        }
        return cl;
    }
}
