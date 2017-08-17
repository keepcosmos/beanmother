package io.beanmother.testmodel;

import java.util.List;

public class Author extends Person {

    private List<Novel> works;
    private String introduction;

    public List<Novel> getWorks() {
        return works;
    }

    public void setWorks(List<Novel> works) {
        this.works = works;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
