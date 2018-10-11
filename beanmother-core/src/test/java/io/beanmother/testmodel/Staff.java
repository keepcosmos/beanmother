package io.beanmother.testmodel;

public class Staff extends Person {
    public enum Position {
        SpecialCoach, Physio, Management, Security, Cook
    }
    private Position position;
    private Team team;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
