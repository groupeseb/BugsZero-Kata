
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.Player;


public class GameRunner {

	private static boolean notAWinner;
	static Random rand = new Random();

	public static void main(String[] args) {
		playGame(rand);
		
	}

	public static void playGame(Random rand) {

		Game aGame = new Game();

		aGame.getPlayers().add(new Player("Chet"));
		aGame.getPlayers().add(new Player("Pat"));
		aGame.getPlayers().add(new Player("Sue"));

		aGame.setCurrentPlayer(aGame.getPlayers().get(0));

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
