
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	// RBR1 Refactoring : Cette variable est seulement utilisée dans la méthode "playGame", elle peut être déplacée directement dans cette méthode
	// private static boolean notAWinner;

	public static void main(String[] args) {
		Random rand = new Random();
		playGame(rand);
		
	}

	public static void playGame(Random rand) {
		Game aGame = new Game();

		aGame.add("Chet");
		aGame.add("Pat");
		aGame.add("Sue");

		// RBR1 : Amélioration possible
		boolean notAWinner;
		do {

			aGame.roll(rand.nextInt(5) + 1);
			
			if (rand.nextInt(9) == 7) {
				notAWinner = aGame.wrongAnswer();
			} else {
				notAWinner = aGame.wasCorrectlyAnswered();
			}



		} while (notAWinner);
	}
}
