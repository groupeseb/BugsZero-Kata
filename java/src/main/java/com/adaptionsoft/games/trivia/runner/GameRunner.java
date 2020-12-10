
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean notAWinner;

	//Add a MANIFEST.MF ?
	public static void main(String[] args) {
		Random rand = new Random();
		playGame(rand);
		
	}

	public static void playGame(Random rand) {
		Game aGame = new Game();
		// Ajout statique de 3 joueurs
		aGame.add("Chet");
		aGame.add("Pat");
		aGame.add("Sue");

		//
		do {

			aGame.roll(rand.nextInt(5) + 1);//+1 au cas où zero 0+1 jusqu'à 5+1

			if (rand.nextInt(9) == 7) {
				notAWinner = aGame.wrongAnswer();
			} else {
				notAWinner = aGame.wasCorrectlyAnswered();
			}



		} while (notAWinner);
	}
}
