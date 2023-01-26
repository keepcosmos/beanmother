package io.beanmother.core;

public class ObjectMother extends AbstractBeanMother {

    private static final ObjectMother beanMother = new ObjectMother();

    public static ObjectMother getInstance() {
        return beanMother;
    }

    private ObjectMother() {
        super();
    }

}
