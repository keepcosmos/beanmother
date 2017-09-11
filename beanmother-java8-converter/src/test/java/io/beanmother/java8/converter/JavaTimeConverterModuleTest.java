package io.beanmother.java8.converter;

import io.beanmother.core.converter.Converter;
import io.beanmother.core.converter.ConverterModule;
import org.junit.Test;

import java.util.Iterator;

/**
 * Test for {@link JavaTimeConverterModule}
 */
public class JavaTimeConverterModuleTest {

    @Test
    public void test() {
        try {
            Class klass = Class.forName("io.beanmother.java8.converter.JavaTimeConverterModule");
            Iterator<Converter> converters = ((ConverterModule) klass.newInstance()).getConverters().iterator();

            while (converters.hasNext()) {
                System.out.println(converters.next());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

}