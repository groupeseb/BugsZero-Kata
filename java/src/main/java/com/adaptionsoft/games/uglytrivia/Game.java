package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
	ArrayList<Player> players = new ArrayList<>();
    
    LinkedList<String> popQuestions = new LinkedList<>();
    LinkedList<String> scienceQuestions = new LinkedList<>();
    LinkedList<String> sportsQuestions = new LinkedList<>();
    LinkedList<String> rockQuestions = new LinkedList<>();
	Player currentPlayer;
    boolean isGettingOutOfPenaltyBox;
    
    public  Game(){

		for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast("Science Question " + i);
			sportsQuestions.addLast("Sports Question " + i);
			rockQuestions.addLast("Rock Question " + i);
		}
    }

	public boolean isPlayable() {
		return players.size() >= 2;
	}

	public boolean add(String playerName) {

		System.out.println(playerName + " was added");
		System.out.println("They are player number " + players.size());
		return true;
	}

	public void roll(int roll) {

		System.out.println(currentPlayer.getName() + " is the current player");
		System.out.println("They have rolled a " + roll);

		if (currentPlayer.isInPenaltyBox()) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;

				System.out.println(currentPlayer.getName() + " is getting out of the penalty box");
				movePlayer(currentPlayer, roll);
				askQuestion(currentPlayer);
			} else {
				System.out.println(currentPlayer.getName() + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
			}

		} else {

			movePlayer(currentPlayer, roll);
			askQuestion(currentPlayer);
		}

	}

	private void movePlayer(Player _player, int _roll) {

		_player.setPlace(_player.getPlace() + _roll);

		if (_player.getPlace() > 11)
			_player.setPlace(_player.getPlace() - 12);

		System.out.println(_player.getName()
                + "'s new location is "
                + _player.getPlace());
	}

	private void askQuestion(Player _player) {

		System.out.println("The category for " + _player.getName() + " is " + currentCategory(_player.getPlace()));

		switch (currentCategory(_player.getPlace())) {
			case "Pop" : System.out.println(popQuestions.removeFirst());
			case "Science" : System.out.println(scienceQuestions.removeFirst());
			case "Sports" : System.out.println(sportsQuestions.removeFirst());
			case "Rock" : System.out.println(rockQuestions.removeFirst());
		}
	}


	private String currentCategory(int _place) {
		switch (_place % 4) {
			case 0 : return "Pop";
			case 1 : return "Science";
			case 2 : return "Sports";
			default : return "Rock";
		}
	}

	public boolean wasCorrectlyAnswered() {
		if (currentPlayer.isInPenaltyBox()){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");

				currentPlayer = nextPlayer(currentPlayer);

				currentPlayer.setPurse(currentPlayer.getPurse() + 1);
				System.out.println(currentPlayer.getName()
						+ " now has "
						+ currentPlayer.getPurse()
						+ " Gold Coins.");

				boolean winner = didPlayerWin();

				return winner;
			} else {

				currentPlayer = nextPlayer(currentPlayer);

				return true;
			}


		} else {

			System.out.println("Answer was corrent!!!!");
			currentPlayer.setPurse(currentPlayer.getPurse() + 1);
			System.out.println(currentPlayer.getName()
					+ " now has "
					+ currentPlayer.getPurse()
					+ " Gold Coins.");

			boolean winner = didPlayerWin();

			currentPlayer = nextPlayer(currentPlayer);

			return winner;
		}
	}

	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(currentPlayer.getName() + " was sent to the penalty box");
		currentPlayer.setInPenaltyBox(true);

		currentPlayer = nextPlayer(currentPlayer);
		return true;
	}

	private Player nextPlayer(Player _player) {

		if (players.indexOf(_player) + 1 != players.size())
			return players.get(players.indexOf(_player) + 1);
		else
			return players.get(0);
	}

	private boolean didPlayerWin() {
		return !(currentPlayer.getPurse() == 6);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
}
