package io.beanmother.testmodel;

import java.util.Calendar;

public class Team {
    private Sports sports;
    private String name;
    private Player[] players;
    private NamedPerson director;
    private Calendar createdAt;

    public Sports getSports() {
        return sports;
    }

    public void setSports(Sports sports) {
        this.sports = sports;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Person getDirector() {
        return director;
    }

    public void setDirector(NamedPerson director) {
        this.director = director;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }
}
