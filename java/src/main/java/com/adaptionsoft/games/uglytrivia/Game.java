package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


/*
Remarques générales sur les choses que je n'ai pas pu coder par manque de temps :

- il faudrait créer une "Player" et refactoriser tout le code afin de l'utiliser
cette classe contiendriat la position du joueur, son argent, son nom, et si il est en "penaltyBox" ou pas.

- il faudrait créer une classe "Question" avec un Enum pour les types de questions; cela permettrait dans le futur
d'avoir de vraies questions facilement, la classe question chargeant un fichiers de questions en fonction de son type.
Cela permettrait d'encapsuler la logique de récupération d'une question et d'éviter les problèmes d'underflow lors de
la récupération d'une nouvelle question, qui peuvent arriver avec les arrays.

- Faire un mécanisme pour récuperer qui a gagné la partie, et l'afficher à la toute fin du jeu.

- Eviter d'utiliser "raw types"
 */
public class Game {
    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] purses = new int[6];
    boolean[] inPenaltyBox = new boolean[6];

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    private static String MSG_CORRECT_ANSWER = "Answer was correct!!!!";

    public Game() {
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast(createRockQuestion(i));
        }
    }

    public String createRockQuestion(int index) {
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
        places[currentPlayer] = (places[currentPlayer] + roll) % 12;

        System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);
        String currentCategory = currentCategory();
        System.out.println("The category is " + currentCategory);
        askQuestion(currentCategory);
    }

    private void askQuestion(String category) {

        switch (category) {
            case "Pop":
                System.out.println(popQuestions.removeFirst());
                break;
            case "Science":
                System.out.println(scienceQuestions.removeFirst());
                break;
            case "Sports":
                System.out.println(sportsQuestions.removeFirst());
                break;
            case "Rock":
                System.out.println(rockQuestions.removeFirst());
                break;
            default:
                System.out.println("Unknown category");
                break;
        }
    }


    private String currentCategory() {
        int playerPlace = places[currentPlayer];

        if (Arrays.asList(0, 4, 8).contains(playerPlace)) {
            return "Pop";
        } else if (Arrays.asList(1, 5, 9).contains(playerPlace)) {
            return "Science";
        } else if (Arrays.asList(2, 6, 10).contains(playerPlace)) {
            return "Sports";
        } else {
            return "Rock";
        }
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {

                correctAnswerEffect();
                // didPlayerWin doit vérifier si c'est le joueur courant qui a gagné, donc avant l'incrémentation
                // du champ "currentPlayer"
                boolean winner = didPlayerWin();
                incrementPlayer();
                return winner;

            /* comment ce cas peut-il arriver ? Si le joueur est en penaltyBox et n'est pas en phase de sortie
            il n'est pas censé avoir la possibilité de répondre à une question */
            } else {
                incrementPlayer();
                return true;
            }

        } else {
            correctAnswerEffect();
            boolean winner = didPlayerWin();
            incrementPlayer();
            return winner;
        }
    }

    public void correctAnswerEffect() {
        System.out.println(MSG_CORRECT_ANSWER);
        purses[currentPlayer]++;
        System.out.println(players.get(currentPlayer)
                + " now has "
                + purses[currentPlayer]
                + " Gold Coins.");
    }

    public void incrementPlayer() {
        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
        return true;
    }

    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == 6);
    }

}
