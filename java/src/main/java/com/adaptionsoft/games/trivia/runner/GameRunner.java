
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean notAWinner;

	public static void main(String[] args) {
		Random rand = new Random();
		playGame(rand);
		
	}

	public static void playGame(Random rand) {
		Game aGame = new Game();

		/*Les joueurs sont codés en durs dans la classe de playGame
			Il serait bon de sortir la génération de joueurs de cette classe (créer une classe enterPlayers par exemple)
			Cette classe permettrait via la console de :
				-Entrer le nom d'un joueur
				-Ajouter un autre joueur ou valider la liste de joueurs
			- Il sera également nécessaire d'ajouter les vérifications suivantes :
				-Il ne sera pas possible de valider une liste de joueurs de moins de 2 joueurs
				-Il faudra limiter le nombre de joueurs à X (valeur à définir)

			Cela permet de personnaliser ses parties avec un nombre et des noms de joueurs dépendants de l'utilisateur
		 */
		aGame.add("Chet");
		aGame.add("Pat");
		aGame.add("Sue");


		do {
			aGame.roll(rand.nextInt(5) + 1);

			/*
				Peu judicieux de choisir si la réponse est correcte au hasard
				La gestion des questions pourra être gérée dans des nouvelles classes (par exemple 'QuestionPop', 'QuestionRock' etc... Héritées d'une classe mère, ainsi qu'une classe gérant la composition d'une liste de questions)
				Il sera également de cette manière plus simple d'ajouter de nouvelles catégories et/ou de trier les catégories souhaitées
			 */
			if (rand.nextInt(9) == 7) {
				notAWinner = aGame.wrongAnswer();
			} else {
				notAWinner = aGame.wasCorrectlyAnswered();
			}
		} while (notAWinner);

		//Ajouter un bout de code pour monter que un joueur a gagné, en l'état, le jeu s'arrête sans indications
	}
}
