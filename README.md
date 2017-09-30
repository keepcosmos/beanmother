# Beanmother

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.beanmother/beanmother-core/badge.svg)](https://search.maven.org/#search%7Cga%7C1%7Cio.beanmother)
[![Build Status](https://travis-ci.org/keepcosmos/beanmother.svg?branch=master)](https://travis-ci.org/keepcosmos/beanmother)
[![Coverage Status](https://coveralls.io/repos/github/keepcosmos/beanmother/badge.svg?branch=master)](https://coveralls.io/github/keepcosmos/beanmother?branch=master)
[![Javadocs](http://javadoc.io/badge/io.beanmother/beanmother-core.svg)](http://javadoc.io/doc/io.beanmother/beanmother-core)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)  

Beanmother helps to create your various and complex objects super easily with fixtures for testing. It encourages developers to write more tests.

Beanmother is a implementation of [ObjectMother](https://martinfowler.com/bliki/ObjectMother.html) pattern and also fixture replacement tool. You do not need to write extra code(like factories or builders) for creating test objects. Beanmother helps to create fresh and randomized bean objects for every types of test. You can use a your bean as it is.

Java 7 and above are supported.

## Installation

* Apache Maven

```xml
<dependency>
    <groupId>io.beanmother</groupId>
    <artifactId>beanmother-core</artifactId>
    <version>0.7.3</version>
    <scope>test</scope>
</dependency>

<!-- For java 8 time and optional datatype -->
<dependency>
    <groupId>io.beanmother</groupId>
    <artifactId>beanmother-java8-converter</artifactId>
    <version>0.7.3</version>
    <scope>test</scope>
</dependency>
```

- Gradle

```groovy
testCompile 'io.beanmother:beanmother-core:0.7.3'

// For java 8 time and optional datatype
testCompile 'io.beanmother:beanmother-java8-converter:0.7.3'
```


## Usage
* [Example](#example)
* [Fixture Script](#fxture-script)
* [Arguments-constructor bean](#arguments-constructor-bean)
* [Post Processor](#post-processor)
* [Customization](#customization)


### Example

Create fixture `.yml` file in `test/resources/fixtures` as a convention.

```yaml
# test/resources/fixtures/publishing.yml

book: &book
  title: ${faker.book.title}
  language: en
  publishedAt: ${faker.date.between('2000-01-01', '2010-01-01')}

author:
  id: ${sequence.number}
  name: ${faker.book.author}
  introduction: ${faker.lorem.paragraph}
  birth: ${faker.date.between('1990-01-01', '2000-01-01')}
  gender: MALE
  works:
    - <<: *book
    - <<: *book
    - <<: *book
```

[YAML format](http://yaml.org/spec/1.1/) is very easy to read and write. It is expressive and extensible. You can use scripts provided by Beanmother to create multiple types of random data and global sequentail numbers.


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

And just create!


### Fixture Script

The scripts provided by Beanmother is a kind of fake property value generator.

```yml
author:
  title: ${faker.book.title}
```

Currently, `FakerScriptRunner` and `SeqenceScriptRunner` are registered as a default.


* `FakerScriptRunner` works with `faker` namespace. the script runner is implemented by [java-faker](https://github.com/DiUS/java-faker). you can find a usage of java-faker in [document](http://dius.github.io/java-faker/apidocs/index.html). If a method has no arguments, you can ignore parentheses. for example,

```yaml
beer:
  hop: ${faker.beer.hop}
  malt: ${faker.beer.malt}
  created_at: ${faker.date.between('1990-01-01', '2000-01-01')}
```


* `SequenceScriptRunner` works with `sequence` namespace. the script generate sequential number globally. for example,

```yaml
person:
  id: ${sequence.number}
```



###  Arguments constructor bean

If a bean does not have no-argument contructor, just add `_construct` key. for example,

```yaml
price:
  _construct:
    - 3
    - USD
```


### PostProcessor

If you need to common configuration for specific beans, you can use [PostProcessor](#register-post-processors).


## Customization

`ObjectMother` class is a default implementation of `AbstractBeanMother` class. For customization, simply extend `AbstractBeanMother`. Highly recommended to build as a singleton instance.  

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

#### Register PostProcessors

A PostProcessor can handle you bean after mapper.

```java
public class AuthorPostProcessor extends PostProcessor<Author> {
    @Override
    public void process(Author bean, FixtureMap fixtureMap) {
        for(Book book : bean.getWorks()) {
            book.setAuthor(bean);
        }
    }
}
```

And pass the instance as a argument when you create a instance.

```java
Author author = ObjectMother.bear("author", Author.class, new AuthorPostProcessor());
```

or, register the PostProcessor to your custom BeanMother for using globally.

```java
@Override
protected void configurePostProcessorFactory(PostProcessorFactory postProcessorFactory) {
    postProcessorFactory.register(new AuthorPostProcessor());
}
```

Everytime you create a instance of Author, `AuthorPostProcessor` will run before return a instance of Author.


#### Customize default fixture path.

Just register the path in ObjectMother. It wil scan all files under the path.

```java
ObjectMother.addFixtureLocation("mocks");
```

Or, override `#defaultFixturePaths` in your custom BeanMother.

```java
@Override
public String[] defaultFixturePaths() {
    // Add your fixture directory or file paths under `resources`.
    return new String[]{ 'test-models', 'fixtures' };
}
```  


#### Customize converter

You can write your own converter for some reason.

```java
public class MyIntegerToStringConverter extends AbstractGenericConverter<Integer, String> {
    @Override
    public String convert(Integer source) {
        return String.valueOf(source + 1);
    }
}
```

And, register the Converter in your custom BeanMother.

```java
@Override
protected void configureConverterFactory(ConverterFactory converterFactory) {
    converterFactory.register(new MyIntegerToStringConverter());
}
```

#### Customize ScriptRunner

You can write your own ScriptRunner

```java
public class MyScriptRunner implements ScriptRunner {

    @Override
    public Object run(ScriptFragment scriptFragment) {
		    // Do something
        return any;
    }

    @Override
    public boolean canHandle(ScriptFragment scriptFragment) {
        return scriptFragment.getMethodName.equal("myname");
    }
}
```

And, register the ScriptRunner in your custom BeanMother.

```java
@Override
protected void configureScriptHandler(ScriptHandler scriptHandler) {
    scriptHandler.register(new MyScriptRunner());     
}
```


## Contributions
Any kind of contributions are very welcome! Coding style guideline is not prepared yet. Although I use Intellij IDE default style, follow a common sense you believe. But please 4 space indentation.
