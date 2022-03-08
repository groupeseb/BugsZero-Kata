package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    List<Player> players = new ArrayList();
    int[] purses  = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];
    // a faire : sortir les element linkedList
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    // récuperer un Player
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public Game(){
    	for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
    }

	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}

	public void add(String playerName) {
    	Player player = new Player(playerName);
	    players.add(player);
	    System.out.println(player.getName() + " was added");
	    System.out.println("They are player number " + howManyPlayers());
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
    	Player player = players.get(currentPlayer);
		System.out.println(player.getName() + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (player.isInPenaltyBox()) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				System.out.println(player.getName() + " is getting out of the penalty box");
				movePlayerAndAskQuestion(player, roll);
			} else {
				System.out.println(player.getName() + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
			}
		} else {
			movePlayerAndAskQuestion(player, roll);
		}
		
	}

	private void movePlayerAndAskQuestion(Player player, int roll) {
    	String currentCategory = currentCategory(player.getPlace());
		player.setPlace(player.getPlace() + roll);
		if (player.getPlace() > 11) {
			player.setPlace(player.getPlace() - 12);
		}

		System.out.println(player.getName()
                + "'s new location is "
                + player.getPlace());
		System.out.println("The category is " + currentCategory);
		askQuestion(currentCategory);
	}

	private void askQuestion(String category) {
    	switch (category) {
			case "Pop":
				System.out.println(popQuestions.removeFirst());
			case "Science":
				System.out.println(scienceQuestions.removeFirst());
			case "Sports":
				System.out.println(sportsQuestions.removeFirst());
			default:
				System.out.println(rockQuestions.removeFirst());
		}
	}
	
	
	private String currentCategory(int place) {
		if (place == 0 || place == 4 || place == 8) return "Pop";
		if (place == 1 || place == 5 || place == 9) return "Science";
		if (place == 2 || place == 6 || place == 10) return "Sports";
		return "Rock";
	}

	// supprimer les current player pour utiliser l'objet Player
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
			
			boolean winner = didPlayerWin();
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
