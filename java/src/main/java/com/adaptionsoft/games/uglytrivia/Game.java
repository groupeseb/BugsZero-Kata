package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Game {

	private final String WRONG_ANSWER_MESSAGE = "Question was incorrectly answered";
	private final String CORRECT_ANSWER_MESSAGE = "Answer was correct!!!!";
	private final String CATEGORY_POP = "Pop";
	private final String CATEGORY_SCIENCE = "Science";
	private final String CATEGORY_SPORTS = "Sports";
	private final String CATEGORY_ROCK = "Rock";
    private final List<String> players = new ArrayList<>();
	private final int PLAYERS_NUMBER = 6;
	private final int QUESTION_NUMBER = 50;

	private final List<Integer> places = new ArrayList<>();
	private final List<Integer> purses = new ArrayList<>();
	private final List<Boolean> inPenaltyBox = new ArrayList<>();

	private final Queue<String> popQuestions = new LinkedList<>();
	private final Queue<String> scienceQuestions = new LinkedList<>();
	private final Queue<String> sportsQuestions = new LinkedList<>();
	private final Queue<String> rockQuestions = new LinkedList<>();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
    	for (int i = 0; i < QUESTION_NUMBER; i++) {
			popQuestions.add("Pop Question " + i);
			scienceQuestions.add("Science Question " + i);
			sportsQuestions.add("Sports Question " + i);
			rockQuestions.add("Rock Question " + i);
    	}
    }

	public boolean addPlayer(String playerName) {

		players.add(playerName);
		places.add(0);
		purses.add(0);
		inPenaltyBox.add(false);

		System.out.println(playerName + " was added");
		System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);

		if (inPenaltyBox.get(currentPlayer)) {
			handlePenaltyBoxPlayer(roll);
		} else {
			movePlayerAndAskQuestion(roll);
		}
	}

	private void handlePenaltyBoxPlayer(int roll) {
		if (roll % 2 != 0) {
			isGettingOutOfPenaltyBox = true;
			System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
			movePlayerAndAskQuestion(roll);
		} else {
			System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
			isGettingOutOfPenaltyBox = false;
		}
	}

	private void movePlayerAndAskQuestion(int roll) {
		places.set(currentPlayer, (places.get(currentPlayer) + roll) % 12);
		System.out.println(players.get(currentPlayer) + "'s new location is " + places.get(currentPlayer));
		System.out.println("The category is " + currentCategory());
		askQuestion();
	}

	private void askQuestion() {
		switch (currentCategory()) {
			case CATEGORY_POP : System.out.println(popQuestions.remove());
			case CATEGORY_SCIENCE : System.out.println(scienceQuestions.remove());
			case CATEGORY_SPORTS : System.out.println(sportsQuestions.remove());
			case CATEGORY_ROCK : System.out.println(rockQuestions.remove());
		}
	}
	
	
	private String currentCategory() {

		String category;
		int position = places.get(currentPlayer);
		switch (position) {
			case 0 :
			case 4 :
			case 8:
				category = CATEGORY_POP;
			case 1:
			case 5:
			case 9:
				category = CATEGORY_SCIENCE;
			case 2:
			case 6:
			case 10:
				category = CATEGORY_SPORTS;
			default :
				category = CATEGORY_ROCK;
		};
		return category;
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox.get(currentPlayer)) {
			if (isGettingOutOfPenaltyBox) {
				return handleCorrectAnswer();
			} else {
				moveToNextPlayer();
				return true;
			}
		} else {
			return handleCorrectAnswer();
		}
	}

	private boolean handleCorrectAnswer() {
		System.out.println("Answer was correct!!!!");
		purses.set(currentPlayer, purses.get(currentPlayer) + 1);
		System.out.println(players.get(currentPlayer) + " now has " + purses.get(currentPlayer) + " Gold Coins.");
		boolean winner = !didPlayerWin();
		moveToNextPlayer();
		return winner;
	}

	private void moveToNextPlayer() {
		currentPlayer = (currentPlayer + 1) % players.size();
	}



	private boolean didPlayerWin() {
		return purses.get(currentPlayer) == PLAYERS_NUMBER;
	}
}
