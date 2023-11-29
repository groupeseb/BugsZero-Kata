package com.adaptionsoft.games.uglytrivia;

public class Player {

    private String name;
    private int place;
    private int purse;
    private boolean inPenaltyBox;

    public Player(String _name) {

        this.name = _name;
        this.place = 0;
        this.purse = 0;
        inPenaltyBox = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getPurse() {
        return purse;
    }

    public void setPurse(int purse) {
        this.purse = purse;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }
}
