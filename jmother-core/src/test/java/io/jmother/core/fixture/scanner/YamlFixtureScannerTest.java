package io.jmother.core.fixture.scanner;

import io.jmother.core.util.Location;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Test for {@link YamlFixtureScanner}
 */
public class YamlFixtureScannerTest {
    private ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    @Test
    public void testSingleFixtureFile() {
        FixtureScanner scanner = new YamlFixtureScanner(classLoader);
        List<File> files = scanner.scan(new Location("fixtures/animals/pets/dog.yml"));

        Assert.assertEquals(files.size(), 1);
        Assert.assertEquals(files.get(0).getName(), "dog.yml");
    }

    @Test
    public void testFixtureFilesInDirectory() {
        FixtureScanner scanner = new YamlFixtureScanner(classLoader);
        List<File> files = scanner.scan(new Location("fixtures/animals/"));
        List fileNames = files.stream().map(file -> file.getName()).collect(Collectors.toList());

        Assert.assertTrue(fileNames.contains("dog.yml"));
        Assert.assertTrue(fileNames.contains("brutals.yml"));
    }

    @Test
    public void testOnlyLoadYAMLFile() {
        FixtureScanner scanner = new YamlFixtureScanner(classLoader);
        List<File> files = scanner.scan(new Location("fixtures"));

        List fileNames = files.stream().map(file -> file.getName()).collect(Collectors.toList());

        Assert.assertTrue(fileNames.contains("dog.yml"));
        Assert.assertFalse(fileNames.contains("unknown.txt"));
    }

    @Test
    public void testFilesystemPath() {
        FixtureScanner scanner = new YamlFixtureScanner(classLoader);
        List<File> files = scanner.scan(new Location("filesystem:src/test/resources/fixtures/animals/pets/dog.yml"));
        Assert.assertTrue(files.size() > 0);
    }
}