package io.beanmother.core.util;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link NumberUtils}
 */
public class NumberUtilsTest {

    @Test
    public void testConvertNumberToTargetClass() throws Exception {
        assertEquals(new Integer(1), NumberUtils.convertNumberToTargetClass(1.1, Integer.class));
        assertEquals(new Byte("1"), NumberUtils.convertNumberToTargetClass(1.1, Byte.class));
        assertEquals(new Float(1.1), NumberUtils.convertNumberToTargetClass(1.1, Float.class));
        assertEquals(new Double(1.1), NumberUtils.convertNumberToTargetClass(1.1, Double.class));
        assertEquals(new Long(1), NumberUtils.convertNumberToTargetClass(1.1, Long.class));
        assertEquals(new Short("1"), NumberUtils.convertNumberToTargetClass(1.1, Short.class));
        assertEquals(new BigInteger("1"), NumberUtils.convertNumberToTargetClass(1, BigInteger.class));
    }

    @Test
    public void testParseNumber() throws Exception {
        assertEquals(new Integer(1), NumberUtils.parseNumber("1", Integer.class));
        assertEquals(new Byte("1"), NumberUtils.parseNumber("1", Byte.class));
        assertEquals(new Float(1.1), NumberUtils.parseNumber("1.1", Float.class));
        assertEquals(new Double(1.1), NumberUtils.parseNumber("1.1", Double.class));
        assertEquals(new Long(1), NumberUtils.parseNumber("1", Long.class));
        assertEquals(new Short("1"), NumberUtils.parseNumber("1", Short.class));
        assertEquals(new BigInteger("1"), NumberUtils.parseNumber("1", BigInteger.class));
    }

}