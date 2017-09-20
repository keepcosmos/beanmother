# beanmother

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.beanmother/beanmother-core/badge.svg)](https://search.maven.org/#search%7Cga%7C1%7Cio.beanmother)
[![Build Status](https://travis-ci.org/keepcosmos/beanmother.svg?branch=master)](https://travis-ci.org/keepcosmos/beanmother)
[![Coverage Status](https://coveralls.io/repos/github/keepcosmos/beanmother/badge.svg?branch=master)](https://coveralls.io/github/keepcosmos/beanmother?branch=master)
[![Javadocs](http://javadoc.io/badge/io.beanmother/beanmother-core.svg)](http://javadoc.io/doc/io.beanmother/beanmother-core)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)  

Beanmother is a kind of class used in testing to help create example objects that you use for testing.

[ObjectMother](https://martinfowler.com/bliki/ObjectMother.html)


## Installation

Apache Maven
```xml
<dependency>
    <groupId>io.beanmother</groupId>
    <artifactId>beanmother-core</artifactId>
    <version>0.7.2</version>
    <scope>test</scope>
</dependency>

<!-- For java 8 converter add additional dependency -->
<dependency>
    <groupId>io.beanmother</groupId>
    <artifactId>beanmother-java8-converter</artifactId>
    <version>0.7.2</version>
    <scope>test</scope>
</dependency>
```

Gradle
```groovy
testCompile 'io.beanmother:beanmother-core:0.7.2'

// For java 8
testCompile 'io.beanmother:beanmother-java8-converter:0.7.2'
```

## Example

Create fixture `.yml` files in `test/resources/fixtures` as a convention.

```yaml
# test/resources/fixtures/publishing.yml

book: &book
  title: ${faker.book.title}
  language: en
  publishedAt: ${faker.date.between('2000-01-01', '2010-01-01')}

author:
  id: ${sequence.number}
  name: ${faker.book.author}
  birth: ${faker.date.between('1990-01-01', '2000-01-01')}
  gender: MALE
  dead: false
  introduction: ${faker.lorem.paragraph}
  works:
    - <<: *book
    - <<: *book
    - <<: *book
```


```java

ObjectMother objectMother = ObjectMother.getInstance();

@Test
public void testSingleObject() {
    Book book = objectMother.bear("book", Book.class);
    Author author = objectMother.bear("author", Author.class);    
}

@Test
public void testMultipleObjects() {
    List<Author> authors = objectMother.bear("author", Author.class, 10);
}

```

## Fixture Script

A script in beanmother is kind of fake property value generator. It used in fixture `.yml` file.

```yml
author:
  title: ${faker.book.title}
```

Currently, `FakerScriptRunner` and `SeqenceScriptRunner` are registered.

* `FakerScriptRunner` works with `faker` namespace. This script runner is from [java-faker](https://github.com/DiUS/java-faker). you can find the ScriptRunner usage. If a method has no arguments, you can ignore parentheses.

* `SequenceScriptRunner` works with `${sequence.number}`. This script return a number that is increased 1 everytime it called globally.

## Customize

For customization, simply extend `AbstractBeanMother`. Highly recommended build ObjectMother as a Singleton instance.  

```java
public class MyObjectMother extends AbstractBeanMother {

    private static MyObjectMother myObjectMother = new MyObjectMother();

    private MyObjectMother() {
        super();
    }
    
    
    // Override for adding your default fixture directory paths
    @Override
    public String[] defaultFixturePaths() {
        return new String[]{ 'test-models', 'fixtures' };
    }
    
    // Override for adding your custom Converter.
    @Override
    protected void configureConverterFactory(ConverterFactory converterFactory) {
        converterFactory.register(new MyConverter());
    }
    
    // Override for adding your custom ScriptRunner.
    @Override
    protected void configureScriptHandler(ScriptHandler scriptHandler) {
        scriptHandler.register(new MyScriptRunner);     
    }
    
    // Override for adding your custom PostProcessor.
    @Override
    protected void configurePostProcessorFactory(PostProcessorFactory postProcessorFactory) {
        postProcessorFactory.register(new MyPostProcessor);
    }
}
```

### Customize default fixture path.

Just register path(it scan all files under the path) to the instance of ObjectMother.

```java
ObjectMother.addFixtureLocation("mocks");
ObjectMother.addFixtureLocation("");
```

Or, override `#defaultFixturePaths` in your custom ObjectMother.

```java
@Override
public String[] defaultFixturePaths() {
    // Add your default fixture dic path
    return new String[]{ 'test-models', 'fixtures' };
}
```  

### Add PostProcessor

A PostProcessor can handle you bean after mapper. 

```java
public class AuthorPostProcessor extends PostProcessor<Author> {
    @Override
    public void process(Author bean, FixtureMap fixtureMap) {
        bean.createdAt(new Date());       
    }
}
``` 

And, register to your custom ObjectMother.


```java
@Override
protected void configurePostProcessorFactory(PostProcessorFactory postProcessorFactory) {
    postProcessorFactory.register(new AuthorPostProcessor);
}
```

For example, 

If you bear a instance of Author, `AuthorPostProcessor` will run before return a instance of Author.


### Customize converter

You can write your own converter for some reason.

```java
public class MyIntegerToStringConverter extends AbstractGenericConverter<Integer, String> {

    @Override
    public String convert(Integer source) {
        return String.valueOf(source + 1);
    }
}
```

And, register to your custom ObjectMother.

```java
@Override
protected void configureConverterFactory(ConverterFactory converterFactory) {
    converterFactory.register(new MyIntegerToStringConverter());
}
```

### Customize ScriptRunner

You can write your own ScriptRunner

```java 
public class MyScriptRunner implements ScriptRunner {
    
    @Override
    public Object run(ScriptFragment scriptFragment) {
        return "Joshua";
    }
    
    @Override
    public boolean canHandle(ScriptFragment scriptFragment) {
        return scriptFragment.getMethodName.equal("myname");
    }
}
```

And, register to your custom ObjectMother.

```java
@Override
protected void configureScriptHandler(ScriptHandler scriptHandler) {
    scriptHandler.register(new MyScriptRunner());     
}
```
