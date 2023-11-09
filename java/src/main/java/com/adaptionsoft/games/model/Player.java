package com.adaptionsoft.games.model;

public class Player {
  protected String name;
  protected boolean inPenaltyBox;

  protected int place;

  protected int purce;

  public Player(String name) {
    this.name = name;
    this.inPenaltyBox = false;
    this.place = 0;
    this.purce = 0;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isInPenaltyBox() {
    return inPenaltyBox;
  }

  public void setInPenaltyBox(boolean inPenaltyBox) {
    this.inPenaltyBox = inPenaltyBox;
  }

  public int getPlace() {
    return place;
  }

  public int getPurce() {
    return purce;
  }

  public void moveForward(int roll) {
    this.place = (this.place + roll) % 12;
  }

  public void displayNewPlace() {
    System.out.println(String.format("%s 's new location is %d", this.name, this.place));
  }

  public void incrementPurse() {
    this.purce += 1;
  }

  public boolean win() {
    this.purce
  }
}
