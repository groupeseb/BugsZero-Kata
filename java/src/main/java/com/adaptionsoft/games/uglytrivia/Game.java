package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
	private final static String POP = "Pop";
	private final static String SCIENCE = "Science";
	private final static String SPORTS = "Sports";
	private final static String ROCK = "Rock";

	List<String> players = new ArrayList<>();
	int[] places = new int[6];
	int[] purses  = new int[6];
	boolean[] inPenaltyBox  = new boolean[6];

	//TODO (AQS): Remplacer cet en instruction par une factory
	LinkedList<String> popQuestions = new LinkedList<>();
	LinkedList<String> scienceQuestions = new LinkedList<>();
	LinkedList<String> sportsQuestions = new LinkedList<>();
	LinkedList<String> rockQuestions = new LinkedList<>();

	int currentPlayer = 0;
	boolean isGettingOutOfPenaltyBox;

	public  Game(){
		for (int i = 0; i < 50; i++) {
			popQuestions.addLast(createQuestion(POP, i));
			scienceQuestions.addLast(createQuestion(SCIENCE, i));
			sportsQuestions.addLast(createQuestion(SPORTS, i));
			rockQuestions.addLast(createQuestion(ROCK, i));
		}
	}

	public String createQuestion(String type, int index){
		return String.format("%s Question %d",type,index);
	}

	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {


		players.add(playerName);
		places[howManyPlayers()] = 0;
		purses[howManyPlayers()] = 0;
		inPenaltyBox[howManyPlayers()] = false;

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

	}

	private void movePlayerAndAskQuestion(int roll) {
		places[currentPlayer] = places[currentPlayer] + roll;
		if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

		System.out.println(players.get(currentPlayer)
				+ "'s new location is "
				+ places[currentPlayer]);
		System.out.println("The category is " + currentCategory());
		System.out.println(askQuestion());
	}

	private String askQuestion() {

		switch (currentCategory()) {
		case POP:
			return popQuestions.removeFirst();
		case SCIENCE:
			return scienceQuestions.removeFirst();
		case SPORTS:
			return sportsQuestions.removeFirst();
		case ROCK:
			return rockQuestions.removeFirst();

		}

		throw new RuntimeException("Invalid category");

	}


	private String currentCategory() {
		if (places[currentPlayer]%4 == 0) return POP;
		if (places[currentPlayer]%4 == 1) return SCIENCE;
		if (places[currentPlayer]%4 == 2) return SPORTS;
		return ROCK;
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

				final boolean winner = didPlayerWin();

				return winner;
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

			final boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;

			return winner;
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
		return !(purses[currentPlayer] == 6);
	}
}
