package io.beanmother.core;

public class ObjectMother extends AbstractBeanMother {

    private final static BeanMother beanMother = new ObjectMother();

    public static BeanMother getInstance() {
        return beanMother;
    }

    private ObjectMother() {
        super();
    }

}
