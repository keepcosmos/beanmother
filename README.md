# Beanmother 

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.beanmother/beanmother-core/badge.svg)](https://search.maven.org/#search%7Cga%7C1%7Cio.beanmother)
[![Build Status](https://travis-ci.org/keepcosmos/beanmother.svg?branch=master)](https://travis-ci.org/keepcosmos/beanmother)
[![Coverage Status](https://coveralls.io/repos/github/keepcosmos/beanmother/badge.svg?branch=master)](https://coveralls.io/github/keepcosmos/beanmother?branch=master)
[![Javadocs](http://javadoc.io/badge/io.beanmother/beanmother-core.svg)](http://javadoc.io/doc/io.beanmother/beanmother-core)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)  


![beanmother](logo.png) 

**Beanmother** helps to create various objects, simple and complex, super easily with fixtures for testing. It encourages developers to write more tests.

Beanmother is an implementation of [ObjectMother](https://martinfowler.com/bliki/ObjectMother.html) pattern and also fixture replacement tool. You do not need to write extra code(like factories or builders) for creating test objects. Beanmother helps to create fresh and randomized bean objects for every type of test. You can use a bean as is!.

Java 7 and above are supported.

## Example

Create fixture `.yml` file in `test/resources/fixtures` as a convention.

```yaml
# test/resources/fixtures/publishing.yml

book: &book
  title: ${faker.book.title}
  language: en
  publishedAt: ${faker.date.between('2000-01-01', '2010-01-01')}

author:
  id: ${sequence.number}
  name: Ernest Hemingway
  introduction: ${faker.lorem.paragraph}
  birth: July 21, 1899
  gender: MALE
  works:
    - <<: *book
    - <<: *book
    - <<: *book
```

[YAML format](http://yaml.org/spec/1.1/) is very easy to read and write. It is expressive and extensible as well. You can use scripts provided by Beanmother to create multiple types of random data and global sequential numbers.


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

But if for some reason, you need load the fixtures from another directory inside the classpath

```java
objectMother.addFixtureLocation("another_dir") // test/resources/another_dir
```

And if the directory it's outside classpath

```java
objectMother.addFixtureLocation("filesystem:///absolute/path/to/fixture/dir") // /absolute/path/to/fixture/dir 
```

## Usage
* [Installation](#installation)
* [Fixture Script](#fixture-script)
* [Arguments-constructor bean](#arguments-constructor-bean)
* [Post Processor](#postprocessor)
* [Customization](#customization)


### Installation

* Apache Maven

```xml
<dependency>
    <groupId>io.beanmother</groupId>
    <artifactId>beanmother-core</artifactId>
    <version>0.9.0</version>
    <scope>test</scope>
</dependency>
```

- Gradle

```groovy
testCompile 'io.beanmother:beanmother-core:0.9.0'
```

#### Extensions

* [beanmother-java8-converter](https://search.maven.org/#artifactdetails|io.beanmother|beanmother-java8-converter|0.9.0|jar) - For java8 time and optional data type

* [beanmother-joda-time-converter](https://search.maven.org/#artifactdetails|io.beanmother|beanmother-joda-time-converter|0.9.0|jar) - For Joda-time data type

* [beanmother-guava-converter](https://search.maven.org/#artifactdetails|io.beanmother|beanmother-guava-converter|0.9.0|jar) - For Google Guava optional data type

* [beanmother-builder-converter](https://search.maven.org/#artifactdetails|io.beanmother|beanmother-builder-converter|0.9.0|jar) - For builder pattern initializer


### Fixture Script

The scripts provided by Beanmother are kind of fake property value generators.

```yml
author:
  title: ${faker.book.title}
```

Currently, `FakerScriptRunner` and `SeqenceScriptRunner` are registered as a default.

* `FakerScriptRunner` works with `faker` namespace. The script runner is implemented by [java-faker](https://github.com/DiUS/java-faker). You can find a usage of java-faker in [this document](http://dius.github.io/java-faker/apidocs/index.html). If a method has no arguments, parentheses can be ignored. For example,

```yaml
beer:
  hop: ${faker.beer.hop}
  malt: ${faker.beer.malt}
  created_at: ${faker.date.between('1990-01-01', '2000-01-01')}
```

* `SequenceScriptRunner` works with the `sequence` namespace. The script generates sequential numbers globally. For example,

```yaml
person:
  id: ${sequence.number}
```



###  Arguments constructor bean

If a bean does not have a no-argument contructor, just add `_construct` key. For example,

```yaml
price:
  _construct:
    - 3
    - USD
```


### PostProcessor

If you need a common configuration for specific beans, you can use [PostProcessor](#register-post-processors).


## Customization

`ObjectMother` class is a default implementation of `AbstractBeanMother` class. For customization, simply extend `AbstractBeanMother`. I highly recommend building it as a singleton instance.

```java
public class MyObjectMother extends AbstractBeanMother {

    private static MyObjectMother myObjectMother = new MyObjectMother();

    private MyObjectMother() {
        super();
    }


    // Override for adding your default fixture directory paths.
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

A PostProcessor can handle your bean after mapper.

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

And, pass the instance as an argument when you create an instance.

```java
Author author = ObjectMother.bear("author", Author.class, new AuthorPostProcessor());
```

or, register the PostProcessor to your custom BeanMother for using it globally.

```java
@Override
protected void configurePostProcessorFactory(PostProcessorFactory postProcessorFactory) {
    postProcessorFactory.register(new AuthorPostProcessor());
}
```

Everytime you create an instance of Author, `AuthorPostProcessor` will run before returning an instance of Author.

#### Customize default fixture path.

Just register the path in ObjectMother. It will scan all files under the path.

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

You can write your own converter if you want to.

```java
public class MyIntegerToStringConverter extends AbstractGenericConverter<Integer, String> {
    @Override
    public String convert(Integer source) {
        return String.valueOf(source + 1);
    }
}
```

And, register the converter in your custom BeanMother.

```java
@Override
protected void configureConverterFactory(ConverterFactory converterFactory) {
    converterFactory.register(new MyIntegerToStringConverter());
}
```

#### Customize ScriptRunner

You can write your own ScriptRunner.

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

## Tests

```
$ mvn test
```

## Contributions

Any kind of contributions are very welcome! Coding style guideline is not prepared yet. Although I use Intellij IDE default style, follow common sense and please use four-space indentation.
