# beanmother

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.beanmother/beanmother-core/badge.svg)](https://search.maven.org/#search%7Cga%7C1%7Cio.beanmother)
[![Build Status](https://travis-ci.org/keepcosmos/beanmother.svg?branch=master)](https://travis-ci.org/keepcosmos/beanmother)
[![Coverage Status](https://coveralls.io/repos/github/keepcosmos/beanmother/badge.svg?branch=ci-setting)](https://coveralls.io/github/keepcosmos/beanmother?branch=ci-setting)
[![Javadocs](http://javadoc.io/badge/io.beanmother/beanmother-core.svg)](http://javadoc.io/doc/io.beanmother/beanmother-core)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)  


Beanmother is a kind of class used in testing to help create example objects that you use for testing.

[ObjectMother](https://martinfowler.com/bliki/ObjectMother.html)



## Example

Create fixture `.yml` files in `test/resources/fixtures`

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

@Test
public void testSingleObject() {
    Book book = ObjectMother.getInstance().bear("book", Book.class);
    Author author = ObjectMother.getInstance().bear("author", Author.class);    
}

@Test
public void testMultipleObjects() {
    List<Book> books = ObjectMother.getInstance().bear("book", Book.class, 10);
    List<Author> authors = ObjectMother.getInstance().bear("author", Author.class, 10);
}

```

