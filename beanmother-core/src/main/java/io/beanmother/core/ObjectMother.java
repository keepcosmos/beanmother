package io.beanmother.core;

public class ObjectMother extends AbstractBeanMother {

    private final static ObjectMother beanMother = new ObjectMother();

    public static ObjectMother getInstance() {
        return beanMother;
    }

    private ObjectMother() {
        super();
    }

}
