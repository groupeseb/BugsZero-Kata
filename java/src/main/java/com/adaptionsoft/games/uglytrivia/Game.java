package com.adaptionsoft.games.uglytrivia;

import java.util.*;

public class Game {
    ArrayList players = new ArrayList();
    Map<Integer, Integer> places = new HashMap<>();
	Map<Integer, Integer> purses = new HashMap<>();
	Map<Integer, Boolean> inPenaltyBox = new HashMap<>();
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int howManyPlayers = 0;
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
		for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(("Rock Question " + i));
		}
    }

	public boolean isPlayable() {
		return (howManyPlayers >= 2);
	}

	public boolean add(String playerName) {

	    players.add(playerName);
	    places.put(howManyPlayers, 0);
	    purses.put(howManyPlayers, 0);
	    inPenaltyBox.put(howManyPlayers, false);

		howManyPlayers = players.size();

	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + howManyPlayers);
		return true;
	}


	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (inPenaltyBox.get(currentPlayer)) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				movePlayerAndAskQuestion(roll);
			} else {
				isGettingOutOfPenaltyBox = false;
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
			}
		} else {
			movePlayerAndAskQuestion(roll);
		}
	}

	private void movePlayerAndAskQuestion(int roll) {
		places.put(currentPlayer, places.get(currentPlayer) + roll);
		if (places.get(currentPlayer) > 11) {
			places.put(currentPlayer, places.get(currentPlayer) - 12);
		}

		System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + places.get(currentPlayer));
		System.out.println("The category is " + currentCategory());
		askQuestion();
	}

	private void askQuestion() {
    	switch(currentCategory()){
			case "Pop" : popQuestions.removeFirst();
			case "Science" : scienceQuestions.removeFirst();
			case "Sports" : sportsQuestions.removeFirst();
			case "Rock" : rockQuestions.removeFirst();
		}
	}
	
	
	private String currentCategory() {
    	switch(places.get(currentPlayer) % 4){
    		case 0 : return "Pop";
			case 1 : return "Science";
			case 2 : return "Sports";
			default : return "Rock";
		}
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox.get(currentPlayer)){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				currentPlayer++;
				if (currentPlayer == players.size()) {
					currentPlayer = 0;
				}
				purses.put(currentPlayer, purses.get(currentPlayer) + 1);
				System.out.println(players.get(currentPlayer)
						+ " now has "
						+ purses.get(currentPlayer)
						+ " Gold Coins.");

				boolean winner = didPlayerWin();

				return winner;
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}
			
			
			
		} else {
		
			System.out.println("Answer was corrent!!!!");
			purses.put(currentPlayer, purses.get(currentPlayer) + 1);
			System.out.println(players.get(currentPlayer) 
					+ " now has "
					+ purses.get(currentPlayer)
					+ " Gold Coins.");
			
			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return winner;
		}
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox.put(currentPlayer, true);
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return !(purses.get(currentPlayer) == 6);
	}
}

