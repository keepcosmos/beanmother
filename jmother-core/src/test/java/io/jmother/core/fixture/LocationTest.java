package io.jmother.core.fixture;

import io.jmother.core.util.Location;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for {@link Location}
 */
public class LocationTest {

    @Test
    public void testDefaultScheme() {
        Location location = new Location("test/fixtures");
        assertTrue(location.isClasspath());
        assertFalse(location.isFilesystemPath());
        assertEquals("classpath:test/fixtures", location.getDescriptor());
        assertEquals("test/fixtures", location.getPath());
    }

    @Test
    public void testClasspathScheme() {
        Location location = new Location("classpath:test/fixtures");
        assertTrue(location.isClasspath());
        assertFalse(location.isFilesystemPath());
        assertEquals("classpath:test/fixtures", location.getDescriptor());
        assertEquals("test/fixtures", location.getPath());
    }

    @Test
    public void testFilesystemScheme() {
        Location location = new Location("filesystem:/User/keepcosmos/test/fixtures");
        assertTrue(location.isFilesystemPath());
        assertFalse(location.isClasspath());
        assertEquals("filesystem:/User/keepcosmos/test/fixtures", location.getDescriptor());
        assertEquals("/User/keepcosmos/test/fixtures", location.getPath());
    }
}