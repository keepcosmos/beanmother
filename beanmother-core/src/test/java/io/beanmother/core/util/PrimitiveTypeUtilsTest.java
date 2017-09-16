package io.beanmother.core.util;

import com.google.common.reflect.TypeToken;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link PrimitiveTypeUtils}
 */
public class PrimitiveTypeUtilsTest {

    @Test
    public void testToWrapper() {
        assertEquals(TypeToken.of(Integer.class), PrimitiveTypeUtils.toWrapperTypeToken(TypeToken.of(int.class)));

        assertEquals(TypeToken.of(Boolean.class), PrimitiveTypeUtils.toWrapperTypeToken(TypeToken.of(boolean.class)));

        assertEquals(TypeToken.of(Float.class), PrimitiveTypeUtils.toWrapperTypeToken(TypeToken.of(float.class)));

        assertEquals(TypeToken.of(Long.class), PrimitiveTypeUtils.toWrapperTypeToken(TypeToken.of(long.class)));

        assertEquals(TypeToken.of(Short.class), PrimitiveTypeUtils.toWrapperTypeToken(TypeToken.of(short.class)));

        assertEquals(TypeToken.of(Byte.class), PrimitiveTypeUtils.toWrapperTypeToken(TypeToken.of(byte.class)));

        assertEquals(TypeToken.of(Double.class), PrimitiveTypeUtils.toWrapperTypeToken(TypeToken.of(double.class)));

        assertEquals(TypeToken.of(Character.class), PrimitiveTypeUtils.toWrapperTypeToken(TypeToken.of(char.class)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void raiseArgumentErrorByNonPrimitiveType() {
        PrimitiveTypeUtils.toWrapper(Integer.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void raiseArgumentErrorByNonWrapperTypeList() {
        List list = new ArrayList();
        PrimitiveTypeUtils.toWrapperListToPrimitiveArray(list, Integer.class);
    }

    @Test
    public void testConvertWrapperListToPrimitiveArray() {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        Object reuslt = PrimitiveTypeUtils.toWrapperListToPrimitiveArray(integerList, new int[]{}.getClass());
        assertTrue(reuslt.getClass().isArray());
        assertTrue(reuslt.getClass().getComponentType().isPrimitive());

        List<Long> longList = new ArrayList<>();
        longList.add(1l);
        reuslt = PrimitiveTypeUtils.toWrapperListToPrimitiveArray(longList, new long[]{}.getClass());
        assertTrue(reuslt.getClass().isArray());
        assertTrue(reuslt.getClass().getComponentType().isPrimitive());

        List<Character> characterList = new ArrayList<>();
        characterList.add('a');
        reuslt = PrimitiveTypeUtils.toWrapperListToPrimitiveArray(characterList, new char[]{}.getClass());
        assertTrue(reuslt.getClass().isArray());
        assertTrue(reuslt.getClass().getComponentType().isPrimitive());

        List<Boolean> booleanList = new ArrayList<>();
        booleanList.add(true);
        reuslt = PrimitiveTypeUtils.toWrapperListToPrimitiveArray(booleanList, new boolean[]{}.getClass());
        assertTrue(reuslt.getClass().isArray());
        assertTrue(reuslt.getClass().getComponentType().isPrimitive());

        List<Float> floatList = new ArrayList<>();
        floatList.add(1.0f);
        reuslt = PrimitiveTypeUtils.toWrapperListToPrimitiveArray(floatList, new float[]{}.getClass());
        assertTrue(reuslt.getClass().isArray());
        assertTrue(reuslt.getClass().getComponentType().isPrimitive());

        List<Double> doubleList = new ArrayList<>();
        doubleList.add(1.0d);
        reuslt = PrimitiveTypeUtils.toWrapperListToPrimitiveArray(doubleList, new double[]{}.getClass());
        assertTrue(reuslt.getClass().isArray());
        assertTrue(reuslt.getClass().getComponentType().isPrimitive());
    }
}