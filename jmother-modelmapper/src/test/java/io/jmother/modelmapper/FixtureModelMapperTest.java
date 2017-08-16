package io.jmother.modelmapper;

import io.jmother.core.mapper.FixtureMapper;
import io.jmother.testmodel.Author;
import io.jmother.testmodel.NamedPerson;
import io.jmother.testmodel.Novel;
import io.jmother.testmodel.Person;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Test for {@link FixtureModelMapper}
 */
public class FixtureModelMapperTest {

    FixtureMapper fixtureMapper = new FixtureModelMapper();

    @Test
    public void testSimpleDataBinding() {
        Map<String, Object> data = new HashMap<>();
        Date date = new Date();
        LocalDate localdate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        data.put("name", "Richard");
        data.put("birth", date);
        data.put("gender", "MALE");
        data.put("dead", "y");

        Person person = fixtureMapper.map(data, Person.class);
        Assert.assertEquals(person.getName(), "Richard");
        Assert.assertEquals(person.getBirth(), localdate);
        Assert.assertEquals(person.getGender(), Person.Gender.MALE);
        Assert.assertTrue(person.isDead());
    }

    @Test
    public void testIgnoreUnknwonProperties() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Joshua");
        data.put("unknownField", 1);

        try {
            Person person = fixtureMapper.map(data, Person.class);
            Assert.assertEquals(person.getName(), "Joshua");
        } catch (Exception e) {
            Assert.assertTrue("mapping error with unknown field", false);
        }
    }

    @Test
    public void testAssociations() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Leo Tolstoy");
        data.put("dead", true);
        List<Map<String, Object>> works = new ArrayList<>();

        Map<String, Object> book1 = new HashMap<>();
        book1.put("title", "War and Peace");
        book1.put("publishedAt", new Date());
        book1.put("language", "ru");

        Map<String, Object> book2 = new HashMap<>();
        book2.put("title", "Anna Karenina");
        book2.put("publishedAt", new Date());
        book2.put("language", "ru");

        works.add(book1);
        works.add(book2);

        data.put("works", works);
        Author author = fixtureMapper.map(data, Author.class);

        Assert.assertEquals("Leo Tolstoy", author.getName());
        Assert.assertTrue(author.isDead());

        Novel novel1 = author.getWorks().get(0);
        Assert.assertEquals("War and Peace", novel1.getTitle());
        Assert.assertTrue(novel1.getPublishedAt() instanceof Date);
        Assert.assertEquals("ru", novel1.getLanguage());

        Novel novel2 = author.getWorks().get(1);
        Assert.assertEquals("Anna Karenina", novel2.getTitle());
        Assert.assertTrue(novel2.getPublishedAt() instanceof Date);
        Assert.assertEquals("ru", novel2.getLanguage());
    }

    @Ignore
    public void testBindingWithConstructor() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Joshua");

        NamedPerson person = fixtureMapper.map(data, NamedPerson.class);
        Assert.assertEquals(person.getName(), "Joshua");
    }
}