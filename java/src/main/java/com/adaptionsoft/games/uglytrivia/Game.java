package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] purses  = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

	public void constructQuestions(){
		for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
		}
	}
	/**
	 * le jeu ne peut se jouer qu' 5 joueurs max sinon dans la fonction add(String playerName) les affectations :
	 *  - inPenaltyBox[currentPlayer] = 0
	 *  - places[currentPlayer] = 0,
	 *  - purses[currentPlayer] = 0
	 * vont génèrer un java.lang.ArrayIndexOutOfBoundsException en ajoutant le 6ème joueur (inPenaltyBox[6], places[6], purses[6] sont OutOfBound)
	 * Donc j'ai conditionné le constructeur en activant la fonction isPlayable (pas appelé auparavant) et en fixant le nbre des joeurs MAX à 5.
	 *
	 * Nous pouvons modifier le code de la fonction add(String playerName) pour faire accepter techniquement 6 joueurs MAX au jeu, mais j'ai préféré garder
	 * le fonctionnement actuel et les eventuelles règles du jeu
	 */
	/**
	 *
	 * Une autre solution serait également de faire 5 constructeur
	 * public Game(String playerName1, String playerName2)
	 * public Game(String playerName1, String playerName2, String playerName3)
	 * public Game(String playerName1, String playerName2, String playerName3, String playerName4)
	 * public Game(String playerName1, String playerName2, String playerName3, String playerName4, String playerName5)
	 *
	 * ça garantit également que le jeu ne peut se jouer qu'à 5 jouers MAX et à 2 joeurs MIN (même à 1 joeur techniquement le jeu ne plante pas)
	 * cette solution est moins extensible, car le jour on veut passer à 6 jouers il faudrait faire 1 constructeur à 6 joeurs,...etc.
	 */
	public Game(String ...playerNames){
		if(isPlayable(playerNames)){
			/**
			 *  la conditions playerNames!= null dans isPlayable(String ...playerNames):
			 *  va garantir que Arrays.asList(playerNames) ne génère pas un NullPointerException car le paramètre dans asList faut qu'il soit non NULL
			 *
			 */
			Arrays.asList(playerNames).forEach(playerName -> {
				add(playerName);
			});
			constructQuestions();
		}else {
			System.out.println("Game is not playable");
			System.exit(1);
		}
	}

	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}

	public boolean isPlayable(String ...playerNames) {

		return ( playerNames!= null && playerNames.length >= 2 && playerNames.length < 6);
	}

	private boolean add(String playerName) {
		
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
		/**
		 *  la condition playerNames.length >= 2 dans isPlayable(String ...playerNames):
		 *  vont garantir que players.get(currentPlayer) ne génère pas un java.lang.ArrayIndexOutOfBoundsException
		 *
		 */
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		/**
		 * inPenaltyBox[currentPlayer] impose un jeu à 5 jouers Max sinon ça génère un java.lang.ArrayIndexOutOfBoundsException
		 * donc j'ai conditionné dans le constructeur le jeu à 5 joueurs Max
		 */
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
		/**
		 * inPenaltyBox[currentPlayer] impose un jeu à 5 jouers Max sinon ça génère un java.lang.ArrayIndexOutOfBoundsException
		 * donc j'ai conditionné dans le constructeur le jeu à 5 joueurs Max
		 * ça plante même avant dans le cas un jeu à > 5 , dans la fonction roll(int roll)
		 */
		places[currentPlayer] = places[currentPlayer] + roll;
		if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

		System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);
		System.out.println("The category is " + currentCategory());
		askQuestion();
	}

	private void askQuestion() {
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

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				/**
				 * C'est le bloc qui pourrait être le déclencheur de l'erreur dans le cas d'un jeu > 5 jouers
				 * arrivons à currentPlayer = 4 , on aurait fait jouer 5 jouers (currentPlayer de 0....4),  le currentPlayer++ fait passer le currentPlayer à 5
				 * le currentPlayer < players.size() dans le cas d'un jeu à plus de 5 joueurs donc on passe au joueur suivant
				 * et au 1er appel de inPenaltyBox[currentPlayer], purses[currentPlayer] ou places[currentPlayer] ça plante
				 */
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				purses[currentPlayer]++;
				System.out.println(players.get(currentPlayer)
						+ " now has "
						+ purses[currentPlayer]
						+ " Gold Coins.");

				boolean winner = didPlayerWin();

				return winner;
			} else {
				/**
				 * C'est le bloc qui pourrait être le déclencheur de l'erreur dans le cas d'un jeu > 5 jouers
				 * arrivons à currentPlayer = 4 , on aurait fait jouer 5 jouers (currentPlayer de 0....4),  le currentPlayer++ fait passer le currentPlayer à 5
				 * le currentPlayer < players.size() dans le cas d'un jeu à plus de 5 joueurs donc on passe au joueur suivant
				 * et au 1er appel de inPenaltyBox[currentPlayer], purses[currentPlayer] ou places[currentPlayer] ça plante
				 */
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
			/**
			 * C'est le bloc qui pourrait être le déclencheur de l'erreur dans le cas d'un jeu > 5 jouers
			 * arrivons à currentPlayer = 4 , on aurait fait jouer 5 jouers (currentPlayer de 0....4),  le currentPlayer++ fait passer le currentPlayer à 5
			 * le currentPlayer < players.size() dans le cas d'un jeu à plus de 5 joueurs donc on passe au joueur suivant
			 * et au 1er appel de inPenaltyBox[currentPlayer], purses[currentPlayer] ou places[currentPlayer] ça plante
			 */
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return winner;
		}
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;
		/**
		 * C'est le bloc qui pourrait être le déclencheur de l'erreur dans le cas d'un jeu > 5 jouers
		 * arrivons à currentPlayer = 4 , on aurait fait jouer 5 jouers (currentPlayer de 0....4),  le currentPlayer++ fait passer le currentPlayer à 5
		 * le currentPlayer < players.size() dans le cas d'un jeu à plus de 5 joueurs donc on passe au joueur suivant
		 * et au 1er appel de inPenaltyBox[currentPlayer], purses[currentPlayer] ou places[currentPlayer] ça plante
		 */
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}
}
