package io.beanmother.core.mapper.setter;

import io.beanmother.core.converter.StandardConverterFactory;
import io.beanmother.core.fixture.FixtureValue;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link FixtureValueSetterMapper}
 */
public class FixtureValueSetterMapperTest {

    FixtureValueSetterMapper mapper;

    @Before
    public void setup() {
        mapper = (FixtureValueSetterMapper) new SetterMapperMediator(new StandardConverterFactory()).getFixtureValuePropertyMapper();
    }

    @Test
    public void testSimpleObjectMapping() {
        SetterObject obj = new SetterObject();
        mapper.map(obj, "integer", new FixtureValue(10));
        assertEquals(obj.getInteger(), new Integer(10));

        mapper.map(obj, "primitiveInt", new FixtureValue(11));
        assertEquals(obj.getPrimitiveInt(), 11);

        Date date = new Date();
        mapper.map(obj, "date", new FixtureValue(date));
        assertEquals(obj.getDate(), date);

        mapper.map(obj, "string", new FixtureValue("test"));
        assertEquals(obj.getString(), "test");
    }

    @Test
    public void testCastingAndMapping() {
        SetterObject obj = new SetterObject();
        mapper.map(obj, "number", new FixtureValue(10));
        assertEquals(obj.getNumber(), new Integer(10));

        mapper.map(obj, "ploat", new FixtureValue(10));
        assertEquals(obj.getPloat(), new Float(10));
    }

    public static class SetterObject {
        private int primitiveInt;
        private Integer integer;
        private Number number;
        private Float ploat;
        private Date date;
        private String string;

        public int getPrimitiveInt() {
            return primitiveInt;
        }

        public void setPrimitiveInt(int primitiveInt) {
            this.primitiveInt = primitiveInt;
        }

        public Integer getInteger() {
            return integer;
        }

        public void setInteger(Integer integer) {
            this.integer = integer;
        }

        public Number getNumber() {
            return number;
        }

        public void setNumber(Number number) {
            this.number = number;
        }

        public Float getPloat() {
            return ploat;
        }

        public void setPloat(Float ploat) {
            this.ploat = ploat;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }
    }
}