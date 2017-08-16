package io.jmother.testmodel;

import java.time.LocalDate;

public class Person {

    public enum Gender {
        MALE, FEMALE, BISEXUAL, ANY;
    }

    private String name;
    private LocalDate birth;
    private Gender gender;
    private boolean dead;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
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
