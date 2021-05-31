
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	// Stocker du "not" dans un booléen n'est pas clair et peut conduire à des erreurs d'interprétation...
	private static boolean winner;

	public static void main(String[] args) {
		Random rand = new Random();
		playGame(rand);

	}

	public static void playGame(Random rand) {
		Game aGame = new Game();

		aGame.add("Chet");
		aGame.add("Pat");
		aGame.add("Sue");

		if(aGame.isPlayable()) {
			do {

				// Ce n'est pas le runner qui doit gérer les roll, cela fait partie du jeu !
				// Pareil pour les réponses correctes ou incorrectes
				winner = aGame.roll();

			} while (!winner);
		}

	}
}
