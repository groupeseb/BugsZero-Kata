package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

/*
Beaucoup de choses à dire sur le développement de ce jeu, je me suis ici concentré sur le pointage (non exhaustif dans le temps imparti) des erreurs et refactoring à faire
Je n'ai changé le code directement que lorsqu'il s'agissait de refactoring simple n'altérant pas le fonctionnement initial du jeu

 */


/*
On peut déplacer toutes les constances textuelles dans un fichier de configuration,
Il sera plus simple de modifier les textes du jeu, et on pourra de ce fait très simplement implémenter des fichiers de traduction
pour faire jouer notre jeu dans différents pays
 */
public class Game {
	//Deviendrait un tableau de Player avec la sortie de la gestion du joueur dans une classe 'Player', d'où un implémentation + simple
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
    
    public  Game(){
    	for (int i = 0; i < 50; i++) {
    		/*
    			La génération de questions n'est pas uniforme entre les questions pop, science, sports et rock
    			Il faudra unifier la génération des questions sur le modèle des rocks questions
    			A déplacer dans les classes de gestion de questions
    			De plus Il sera nécessaire de garder un tableau des questions initial que l'on pourra réutiliser si toutes les questions ont été posée (simulation de remélange des questions dans la pile de questions)
    		 */
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
    }

	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}

	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	/*
		Ce serait une bonne idée de créer une classe 'Player' avec les attributs suivants :
			-nom
			-places
			-purses
			-inPenaltyBox
		On pourrait alors directement influer sur l'objet Player correspondant au lieu d'accéder à ces informations depuis la classe Game
		Il sera également nécessaire de revoir la liste des joueurs du jeu afin de pouvoir passer au joueur suivant
	 */
	public boolean add(String playerName) {
		
		
	    players.add(playerName);
	    /*
	    	Il est dangeureux d'accéder à ces liste via index (on risque une exception indexOutOfBound)
	    	De plus en procédant de cette manière, l'index 0 des tableaux n'est jamais utilisé !
	    	On peut corriger cela et améliorer la lecture du code en utilisant une HashMap<String, Integer> au lieu de ces tableaux
	    	La Hashmap permet en effet d'acéder directement aux informations du joueur en entrant son nom dans la Map.
	    	Problème -> impossibilité d'avoir 2 joueurs de même nom. Il faudra alors ajouter un controle dans la classe de générationdesJoueurs pour empécher
	    	l'existence de 2 joueurs de même noms (ce qui évitera aussi un soucis de lecture lors de l'utilisation du jeu)
	    	----> Ce soucis est également automatiquement résolu par l'utilisation d'une nouvelle classe 'Player' comme vu ci dessus (meilleur Fix selon moi)<-----
	     */
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
				inPenaltyBox[currentPlayer] = false;
				
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				movePlayerAndAskQuestion(roll);
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
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
		/*
			A partir d'ici, currentCategory va être appelé plusieures fois, il sera nécessaire de la stocker dans une variable
			et ainsi de passer la catégorie en paramètre de askQuestion
		 */
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
		//Très peu lisible, remplaçable par un switch pour un peu + de lisibilité
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
			if (!inPenaltyBox[currentPlayer]) {
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
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	//Peu logique, on retourne false si un joueur a gagné, ce n'est pas cohérent avec le nom de la fonction
	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}
}
