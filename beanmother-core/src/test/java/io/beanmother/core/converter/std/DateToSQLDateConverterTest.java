package io.beanmother.core.converter.std;

import com.google.common.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link DateToSQLDateConverter}
 */
public class DateToSQLDateConverterTest {

    DateToSQLDateConverter converter = new DateToSQLDateConverter();

    @Test
    public void testCanHandle() {
        assertTrue(converter.canHandle(new Date(), TypeToken.of(java.sql.Date.class)));

        assertFalse(converter.canHandle(new Date(), TypeToken.of(Object.class)));
    }

    @Test
    public void testConvert() {
        Date date = new Date();

        java.sql.Date sqlDate = converter.convert(date);
        Assert.assertEquals(date.getTime(), sqlDate.getTime());
    }

}