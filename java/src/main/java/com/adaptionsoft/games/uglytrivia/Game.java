package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Game {
	// RBR12 : Attributs privés, utilisation des interfaces Collection<> et/ou List<> pour la déclaration de variables pour plus de flexibilité sur les implémentations

    ArrayList players = new ArrayList();
    // RBR13 Refactoring : Utiliser des listes au lieu des tableaux fixes car on ne peut jouer qu'à 6 joueurs max ici
    int[] places = new int[6];
    int[] purses  = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    // RBR8 : Déclaration de catégories
		enum Category
		{
			Pop, Science, Sports, Rock;
		}
		List<Category> categories = Arrays.asList(Category.values());

		private static final String CORRECT = "Answer was corrent!!!!";
    
    public  Game(){
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

	// RBR2 : Méthode non utilisée => l'utiliser dans la méthode "roll"
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	// RBR6 : retourne "boolean" mais jamais utilisé => trouver une utilisation ou mettre "void"
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
		// RBR2 Bug : s'il n'y a pas de joueur, il y aura une ArrayIndexOutOfBoundsException
//		System.out.println(players.get(currentPlayer) + " is the current player");
		// RBR2 Correctif : Vérifier la présence d'au moins 2 joueurs via la méthode "isPlayable"
		if (!isPlayable())
		{
			throw new RuntimeException("Il n'y a pas assez de joueur pour pouvoir jouer");
		}

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
		// RBR3 : Problème de conception. On ne peut aller qu'à 11 cases sur la piste pour déterminer la catégorie via la méthode "currentCategory" 
		// RBR4 Refactoring : Supprimer cette ligne et réécrire la méthode "currentCategory"
//		if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

		System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);
		System.out.println("The category is " + currentCategory());
		askQuestion();
	}

	// RBR7 Refactoring : faire un switch sur la catégorie
	// RBR8 Refactoring : déclarer des constantes/enum pour les catégories
	private void askQuestion() {
//		if (currentCategory() == "Pop")
//			System.out.println(popQuestions.removeFirst());
//		if (currentCategory() == "Science")
//			System.out.println(scienceQuestions.removeFirst());
//		if (currentCategory() == "Sports")
//			System.out.println(sportsQuestions.removeFirst());
//		if (currentCategory() == "Rock")
//			System.out.println(rockQuestions.removeFirst());

		switch ( currentCategory() )
		{
			case Pop:
				System.out.println(popQuestions.removeFirst());
				break;

			case Science:
				System.out.println(scienceQuestions.removeFirst());
				break;

			case Sports:
				System.out.println(sportsQuestions.removeFirst());
				break;

			default:
				System.out.println(rockQuestions.removeFirst());
				break;
		}
	}
	
	// RBR5 : Le fait d'utiliser un tableau pour déterminer la catégorie ne permet pas d'avoir une piste à X cases, X pouvant aller potentiellement à l'infini
	// RBR5 Proposition : Déterminer la catégorie provenant d'une liste sur un modulo 4 par exemple pour gagner nombre de lignes et en lisibilité
	private Category currentCategory() {
//		if (places[currentPlayer] == 0) return "Pop";
//		if (places[currentPlayer] == 4) return "Pop";
//		if (places[currentPlayer] == 8) return "Pop";
//		if (places[currentPlayer] == 1) return "Science";
//		if (places[currentPlayer] == 5) return "Science";
//		if (places[currentPlayer] == 9) return "Science";
//		if (places[currentPlayer] == 2) return "Sports";
//		if (places[currentPlayer] == 6) return "Sports";
//		if (places[currentPlayer] == 10) return "Sports";
//		return "Rock";
		return categories.get(places[currentPlayer] % 4);
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println(CORRECT);
				// RBR9 Refactoring : Code dupliqué plusieurs fois => création d'une méthode "changeCurrentPlayer"
//				currentPlayer++;
//				if (currentPlayer == players.size()) currentPlayer = 0;
				purses[currentPlayer]++;
				System.out.println(players.get(currentPlayer)
						+ " now has "
						+ purses[currentPlayer]
						+ " Gold Coins.");

				// RBR10 Refactoring : Retourner directement sans passer par une variable
//				boolean winner = didPlayerWin();
//				return winner;

				return didPlayerNotWin();

				// RBR14 : le 'else' n'est pas nécessaire ici
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}

		} else {
		
			// RBR15 : erreur de typo => Déclaration constante
//			System.out.println("Answer was corrent!!!!");
			System.out.println(CORRECT);
			purses[currentPlayer]++;
			System.out.println(players.get(currentPlayer) 
					+ " now has "
					+ purses[currentPlayer]
					+ " Gold Coins.");
			
//			currentPlayer++;
//			if (currentPlayer == players.size()) currentPlayer = 0;
			changeCurrentPlayer();

//			boolean winner = didPlayerNotWin();
//			return winner;
			return didPlayerNotWin();
		}
	}

	// RBR9 : création méthode
	private void changeCurrentPlayer()
	{
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	// RBR11 : Logique inversée => source de problème
	// RBR11 Proposition :
	// - renommer en "didPlayerNotWin"
	// OU
	// - renommer variable "notAWinner" en "winner" dans GameRunner et modifier le "while(!winner)"
//	private boolean didPlayerWin() {
	private boolean didPlayerNotWin() {
		return !(purses[currentPlayer] == 6);
	}
}
