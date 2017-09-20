package io.beanmother.core.loader.scanner;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Scanner to find .yaml or .yml prefixed files.
 */
public class YamlFixtureScanner extends AbstractFixtureScanner {

    private static Pattern extensionPattern = Pattern.compile("(\\.)(yml|yaml)$", Pattern.CASE_INSENSITIVE);

    /**
     * Create a YamlFixtureScanner wiath a ClassLoader.
     * @param classLoader the ClassLoader
     */
    public YamlFixtureScanner(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected boolean isFixtureFile(File file) {
        if (!super.isFixtureFile(file)) return false;
        Matcher matcher = extensionPattern.matcher(file.getName());
        return matcher.find();
    }
}
