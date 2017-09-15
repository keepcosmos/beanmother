package io.beanmother.core.converter.std;

import com.google.common.reflect.TypeToken;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link DateToCalendarConverter}
 */
public class DateToCalendarConverterTest {

    DateToCalendarConverter converter = new DateToCalendarConverter();

    @Test
    public void testCanHandle() {
        assertTrue(converter.canHandle(new Date(), TypeToken.of(Calendar.class)));
        assertFalse(converter.canHandle("09/03/2017", TypeToken.of(Calendar.class)));
        assertFalse(converter.canHandle(new Date(), TypeToken.of(Date.class)));
    }

    @Test
    public void testConvert() throws ParseException {
        String dateString = "06/27/2017";
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date date;
        date = df.parse(dateString);

        Calendar cal = converter.convert(date);
        assertEquals(date.getTime(), cal.getTime().getTime());

        dateString = "01/02/2016";
        date = df.parse(dateString);
        cal = converter.convert(date);
        assertEquals(date.getTime(), cal.getTime().getTime());
    }
}