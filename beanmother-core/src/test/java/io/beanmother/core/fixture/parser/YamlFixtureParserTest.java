package io.beanmother.core.fixture.parser;

import io.beanmother.core.fixture.FixtureMap;
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

        Map<String, FixtureMap> data = parser.parse(str);
//        Map<String, Object> beanmotherData = (Map<String, Object>) data.get("beanmother");
//        Assert.assertEquals(beanmotherData.get("id"), 1);
//        Assert.assertEquals(beanmotherData.get("title"), "beanmother");
//        Assert.assertEquals(beanmotherData.get("url"), "https://github.com/keepcosmos/beanmother");
    }
}