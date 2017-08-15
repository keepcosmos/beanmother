package io.jmother.core.fixture;

/**
 * A path information for fixture(s)
 */
public class Location {

    /**
     * Path prefix for classpath
     */
    private static final String CLASSPATH_SCHEME = "classpath:";

    /**
     * Path prefix for filesystem path
     */
    private static final String FILESYSTEM_SCHEME = "filesystem:";

    private String scheme;

    private String path;

    public Location(String path) {
        assignSchemeAndPath(path);
    }

    /**
     * @return The complete location.
     */
    public String getDescriptor() {
        return this.scheme + this.path;
    }

    /**
     * @return The path.
     */
    public String getPath() {
        return this.path;
    }

    /**
     * @return Check the location is classpath or not.
     */
    public boolean isClasspath() {
        return CLASSPATH_SCHEME.equals(this.scheme);
    }

    /**
     * @return Check the location is filesystem path or not.
     */
    public boolean isFilesystemPath() {
        return FILESYSTEM_SCHEME.equals(this.scheme);
    }

    /**
     * @return The complete location as string.
     */
    @Override
    public String toString() {
        return this.getDescriptor();
    }

    @Override
    public int hashCode() {
        return getDescriptor().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if ( obj == null || getClass() != obj.getClass()) return false;
        return hashCode() == obj.hashCode();
    }

    private void assignSchemeAndPath(String path) {
        String normalizedPath = path.trim().replace("\\", "/");
        int pathStartIndex = normalizedPath.indexOf(":");

        if (pathStartIndex == -1 ) {
            this.scheme = CLASSPATH_SCHEME;
        } else {
            this.scheme = normalizedPath.substring(0, pathStartIndex + 1);
        }

        this.path = normalizedPath.substring(pathStartIndex + 1);

        if (this.path.endsWith("/")) {
            this.path = this.path.substring(0, path.length() - 1);
        }

        if (!isClasspath() && !isFilesystemPath()) {
            throw new IllegalArgumentException(path + " is unkown path, must be either 'filesystem:' or 'classpath:'");
        }
    }

}

