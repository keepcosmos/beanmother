package io.beanmother.testmodel;

import java.util.Date;

public class Person {

    public enum Gender {
        MALE, FEMALE, BISEXUAL, ANY;
    }

    private int id;

    private String name;

    private Date birth;

    private Gender gender;

    private boolean dead;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
