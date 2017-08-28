package io.beanmother.core.fixture.parser;

import io.beanmother.core.fixture.FixtureList;
import io.beanmother.core.fixture.FixtureMap;
import io.beanmother.core.fixture.FixtureValue;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link YamlFixtureParser}
 * It does not test all cases that {@link org.yaml.snakeyaml} done already.
 */
public class YamlFixtureParserTest {
    YamlFixtureParser parser = new YamlFixtureParser();

    @Test
    public void testParse() throws IOException {
        Path path = Paths.get(ClassLoader.getSystemClassLoader().getResource("fixtures/this.yml").getFile());
        String fixtureStr = new String(Files.readAllBytes(path));

        Map<String, FixtureMap> fixtureMaps = parser.parse(fixtureStr);

        FixtureMap beanmother = fixtureMaps.get("beanmother");
        assertTrue(beanmother.isRoot());
        assertEquals(beanmother.getFixtureName(), "beanmother");

        assertTrue(beanmother.get("id") instanceof FixtureValue);
        assertEquals(beanmother.get("id"), new FixtureValue(1));
        assertEquals(beanmother.get("title"), new FixtureValue("beanmother"));
        assertEquals(beanmother.get("url"), new FixtureValue("https://github.com/keepcosmos/beanmother"));
        assertTrue(beanmother.get("authors") instanceof FixtureList);
    }

    @Test(expected = FixtureFormatException.class)
    public void testFailParseWhenFixtureIsList() {
        String fixtureStr = "person:\n  - JH Shin\n  - John";
        parser.parse(fixtureStr);
    }

    @Test(expected = FixtureFormatException.class)
    public void testFailParseWhenFixtureIsStringValue() {
        String fixtureStr = "person: test1\nanimal: test2";
        parser.parse(fixtureStr);
    }
}