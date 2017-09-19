package io.beanmother.testmodel;

public class Coffee {
    public enum Bean {
        BlueMountain, Catuai, Charrieriana, Java
    }

    public long id;
    public String seller;
    public Price price;
    public Bean bean;
    public boolean roasted;
}
