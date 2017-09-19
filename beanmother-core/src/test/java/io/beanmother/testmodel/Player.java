package io.beanmother.testmodel;

import com.github.javafaker.Team;

public class Player extends Person {

    private int number;

    private Team team;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
