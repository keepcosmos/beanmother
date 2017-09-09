# beanmother

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
    Author author = ObjectMother.getInstance().bear("author", Author.class);    
}

@Test
public void testMultipleObjects() {
    List<Author> authors = Object.mother.getInstance().bear("author", Author.class, 10);
}

```

