package io.beanmother.core.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Test for {@link PrimitiveTypeUtils}
 */
public class PrimitiveTypeUtilsTest {

    @Test
    public void testConvertWrapperListToPrimitiveArray() {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);

        Object reuslt = PrimitiveTypeUtils.toWrapperListToPrimitiveArray(integerList, new int[]{}.getClass());
        assertTrue(reuslt.getClass().isArray());
        assertTrue(reuslt.getClass().getComponentType().isPrimitive());

        List<Character> characterList = new ArrayList<>();
        characterList.add('a');
        characterList.add('b');

        reuslt = PrimitiveTypeUtils.toWrapperListToPrimitiveArray(characterList, new char[]{}.getClass());
        assertTrue(reuslt.getClass().isArray());
        assertTrue(reuslt.getClass().getComponentType().isPrimitive());

        List<Boolean> booleanList = new ArrayList<>();
        booleanList.add(true);
        booleanList.add(false);

        reuslt = PrimitiveTypeUtils.toWrapperListToPrimitiveArray(booleanList, new boolean[]{}.getClass());
        assertTrue(reuslt.getClass().isArray());
        assertTrue(reuslt.getClass().getComponentType().isPrimitive());
    }
}