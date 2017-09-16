package io.beanmother.core.converter.std;

import com.google.common.reflect.TypeToken;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Test for {@link StringToCalendarConverter}
 */
public class StringToCalendarConverterTest {

    StringToCalendarConverter converter = new StringToCalendarConverter();

    @Test
    public void testCanHandle() {
        assertTrue(converter.canHandle("2017-01-01", TypeToken.of(Calendar.class)));
        assertFalse(converter.canHandle("2017-01-01", TypeToken.of(Date.class)));
    }

    @Test
    public void testConvert() {
        Calendar cal = (Calendar) converter.convert("2017-01-02");
        assertEquals(2017, cal.get(Calendar.YEAR));
        assertEquals(0, cal.get(Calendar.MONTH));
        assertEquals(2, cal.get(Calendar.DAY_OF_MONTH));
    }
}