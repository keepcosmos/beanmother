package io.beanmother.core.util;

import com.google.common.reflect.TypeToken;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link TypeTokenUtils}
 */
public class TypeTokenUtilsTest {

    @Test
    public void testGetGenericTypeTokens() {
        TypeToken source = new TypeToken<List<String>>() {};
        List<TypeToken<?>> typeTokens = TypeTokenUtils.extractGenericTypeTokens(source);

        assertEquals( 1, typeTokens.size());
        assertEquals(TypeToken.of(String.class), typeTokens.get(0));
    }

    @Test
    public void testGetMultipleGenericTypeTokens() {
        TypeToken source = new TypeToken<Map<String, Integer>>() {};
        List<TypeToken<?>> typeTokens = TypeTokenUtils.extractGenericTypeTokens(source);

        assertEquals(2, typeTokens.size());
        assertEquals(typeTokens.get(0), TypeToken.of(String.class));
        assertEquals(typeTokens.get(1), TypeToken.of(Integer.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRaiseException_extractElementTypeToken() {
        TypeTokenUtils.extractElementTypeToken(TypeToken.of(Integer.class));
    }
}