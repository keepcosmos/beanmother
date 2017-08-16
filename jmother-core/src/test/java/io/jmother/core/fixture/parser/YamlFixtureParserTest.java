package io.jmother.core.fixture.parser;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Test for {@link YamlFixtureParser}
 * It does not test all cases that {@link org.yaml.snakeyaml} done already.
 */
public class YamlFixtureParserTest {
    YamlFixtureParser parser = new YamlFixtureParser();

    @Test
    public void testParse() throws IOException {
        Path path = Paths.get(ClassLoader.getSystemClassLoader().getResource("fixtures/this.yml").getFile());
        String str = new String(Files.readAllBytes(path));

        Map<String, Object> data = parser.parse(str);
        Map<String, Object> jmotherData = (Map<String, Object>) data.get("jmother");
        Assert.assertEquals(jmotherData.get("id"), 1);
        Assert.assertEquals(jmotherData.get("title"), "JMother");
        Assert.assertEquals(jmotherData.get("url"), "https://github.com/keepcosmos/jmother");
    }
}