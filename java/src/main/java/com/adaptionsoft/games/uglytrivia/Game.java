package com.adaptionsoft.games.uglytrivia;

import java.util.*;

public class Game {
	// La list accepte les doublons, il faut utiliser Set dans ce cas
    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] purses  = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];

	List<String> popQuestions = new ArrayList<>(50);
	List<String> scienceQuestions = new ArrayList<>(50);
	List<String> sportsQuestions = new ArrayList<>(50);
	List<String> rockQuestions = new ArrayList<>(50);

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    // ajout du java documentation pour simplifier la lecture et la maintenance
    public  Game(){
    	for (int i = 0; i < 50; i++) {
			popQuestions.add("Pop Question " + i);
			scienceQuestions.add(("Science Question " + i));
			sportsQuestions.add(("Sports Question " + i));
			rockQuestions.add(createRockQuestion(i));
    	}
    }

	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}

	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {
		
		
	    players.add(playerName);
	    places[howManyPlayers()] = 0;
	    purses[howManyPlayers()] = 0;
	    inPenaltyBox[howManyPlayers()] = false;

	    // utilisation d'un système de log au lieu de 'System.out.println'
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		if (isPlayable()) {
			System.out.println(players.get(currentPlayer) + " is the current player");
			System.out.println("They have rolled a " + roll);

			if (inPenaltyBox[currentPlayer]) {
				if (roll % 2 != 0) {
					isGettingOutOfPenaltyBox = true;

					System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
					movePlayerAndAskQuestion(roll);
				} else {
					System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
					isGettingOutOfPenaltyBox = false;
				}

			} else {

				movePlayerAndAskQuestion(roll);
			}
		} else {
			System.err.println("Le nombre maximum de joueurs n'est pas atteint ");
		}
		
	}

	private void movePlayerAndAskQuestion(int roll) {
		places[currentPlayer] = places[currentPlayer] + roll;
		if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

		System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);
		System.out.println("The category is " + currentCategory().getCategory());
		askQuestion();
	}

	private void askQuestion() {
		switch (currentCategory()) {
			case POP:
				System.out.println(popQuestions.remove(0));
				break;
			case SCIENCE:
				System.out.println(scienceQuestions.remove(0));
				break;
			case SPORTS:
				System.out.println(sportsQuestions.remove(0));
				break;
			default:
				System.out.println(rockQuestions.remove(0));
				break;
		}
	}
	
	
	private QuestionCategory currentCategory() {
		switch (places[currentPlayer]) {
			case 0:
			case 4:
			case 8:
				return QuestionCategory.POP;
			case 1:
			case 5:
			case 9:
				return QuestionCategory.SCIENCE;
			case 2:
			case 6:
			case 10:
				return QuestionCategory.SPORTS;
			default:
				return QuestionCategory.ROCK;
		}
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				purses[currentPlayer]++;
				System.out.println(players.get(currentPlayer)
						+ " now has "
						+ purses[currentPlayer]
						+ " Gold Coins.");

				return didPlayerWin();
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}
			
			
			
		} else {
		
			System.out.println("Answer was corrent!!!!");
			purses[currentPlayer]++;
			System.out.println(players.get(currentPlayer) 
					+ " now has "
					+ purses[currentPlayer]
					+ " Gold Coins.");
			
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return didPlayerWin();
		}
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return purses[currentPlayer] != 6;
	}
}
