package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    private final ArrayList<String> players = new ArrayList<>();

    // il faut rajouter une vérification sur les joueurs pour éviter un OutOfBoundException si > 6 joueurs
    private static final int MAX_PLAYERS_NUMBER = 6;

    int[] places = new int[6];
    int[] purses  = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];
    
    LinkedList<String> popQuestions = new LinkedList<>();
    LinkedList<String> scienceQuestions = new LinkedList<>();
    LinkedList<String> sportsQuestions = new LinkedList<>();
    LinkedList<String> rockQuestions = new LinkedList<>();

    private static final int NUMBER_OF_TURN = 50;
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
    	for (int turnNo = 0; turnNo < NUMBER_OF_TURN; turnNo++) {
			popQuestions.addLast("Pop Question " + turnNo);
			scienceQuestions.addLast(("Science Question " + turnNo));
			sportsQuestions.addLast(("Sports Question " + turnNo));
			rockQuestions.addLast(createRockQuestion(turnNo));
    	}
    }

	// soit on fait des méthodes pour chaque type de question, soit on met tout en dur directement dans le constructeur
	private String createRockQuestion(int index){
		return "Rock Question " + index;
	}

	public boolean isPlayable() {
    	// peut-être rajouté un contrôle pour vérifier qu'il reste des questions dans les listes ?
		return (howManyPlayers() >= 2);
	}

	// je n'ai pas l'impression qu'on ai besoin du retour
	public void add(String playerName) {

    	// rajouter vérification nombre de joueurs, si > MAX alors exception
	    players.add(playerName);

	    int playerNo = howManyPlayers();

	    places[playerNo] = 0;
	    purses[playerNo] = 0;
	    inPenaltyBox[playerNo] = false;
	    
	    System.out.println(playerName + " was added. He is player " + playerNo);
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
    	if(!isPlayable()) {
    		// pour bien faire il faudrait faire une exception custom
    		throw new RuntimeException("Not enough players");
		}

    	String currentPlayerName = players.get(currentPlayer);
		System.out.println(currentPlayerName + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (inPenaltyBox[currentPlayer]) {

			// peut-êre découper cette partie dans une sous fonction handlePenaltyBox
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(currentPlayerName + " is getting out of the penalty box");
				movePlayerAndAskQuestion(roll);
			} else {
				System.out.println(currentPlayerName + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
			}
			
		} else {

			movePlayerAndAskQuestion(roll);
		}
		
	}

	private void movePlayerAndAskQuestion(int roll) {

    	// algo assez compliqué à suivre, il faudrait faire des constantes ou des commentaires

		// déplace le joueur du résultat du dés
		places[currentPlayer] = places[currentPlayer] + roll;

		// si nouvelle position du joueur > 11
		if (places[currentPlayer] > 11) {

			// on le recule de 12
			places[currentPlayer] = places[currentPlayer] - 12;
		}

		System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);
		System.out.println("The category is " + currentCategory());
		askQuestion();
	}

	private void askQuestion() {

    	String currentCategory = currentCategory();
    	// faire un switch case en utilisant la variable définie au dessus
		if (currentCategory() == "Pop")
			System.out.println(popQuestions.removeFirst());
		if (currentCategory() == "Science")
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory() == "Sports")
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory() == "Rock")
			System.out.println(rockQuestions.removeFirst());		
	}
	
	
	private String currentCategory() {
    	// faire un switch case avec "Rock" en default plutôt
		if (places[currentPlayer] == 0) return "Pop";
		if (places[currentPlayer] == 4) return "Pop";
		if (places[currentPlayer] == 8) return "Pop";
		if (places[currentPlayer] == 1) return "Science";
		if (places[currentPlayer] == 5) return "Science";
		if (places[currentPlayer] == 9) return "Science";
		if (places[currentPlayer] == 2) return "Sports";
		if (places[currentPlayer] == 6) return "Sports";
		if (places[currentPlayer] == 10) return "Sports";
		return "Rock";
	}

	// méthode complexe, à splitter en plusieurs
	public boolean wasCorrectlyAnswered() {

    	// une fonction handlePenaltyBox
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				playerTurnEnd();
				purses[currentPlayer]++;
				System.out.println(players.get(currentPlayer)
						+ " now has "
						+ purses[currentPlayer]
						+ " Gold Coins.");

				boolean winner = didPlayerWin();

				return winner;
			} else {
				playerTurnEnd();
				return true;
			}
			
			
			
		} else {
		
			System.out.println("Answer was correct!!!!");
			purses[currentPlayer]++;
			System.out.println(players.get(currentPlayer) 
					+ " now has "
					+ purses[currentPlayer]
					+ " Gold Coins.");
			
			boolean winner = didPlayerWin();
			playerTurnEnd();
			
			return winner;
		}
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;

		playerTurnEnd();
		return true;
	}

	private void playerTurnEnd() {

		if (this.isLastPlayer()) {
			currentPlayer = 0;
		} else {
			currentPlayer++;
		}
	}

	private boolean isLastPlayer() {
		return currentPlayer == players.size();
	}


	private boolean didPlayerWin() {

    	// c'est pas plutôt l'inverse ? si = 6 alors il a gagné ?
		return !(purses[currentPlayer] == 6);
	}
}
