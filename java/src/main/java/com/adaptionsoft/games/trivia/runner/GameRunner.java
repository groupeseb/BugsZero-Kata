
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.Player;


public class GameRunner {

	private static boolean gameInProgress;
	static Random rand = new Random();

	public static void main(String[] args) {
		playGame(rand);
	}

	public static void playGame(Random rand) {

		Game aGame = new Game();
		gameInProgress = true;

		aGame.addPlayer(new Player("Chet"));
		aGame.addPlayer(new Player("Pat"));
		aGame.addPlayer(new Player("Sue"));

		aGame.setCurrentPlayer(aGame.getPlayers().get(0));

		do {

			aGame.roll(rand.nextInt(5) + 1);

			// TODO : ici faire une fonction answer(boolean) qui prendrait en parametre si la reponse est juste ou non (et par la suite une question + sa reponse)
			if (rand.nextInt(9) == 7) {
				aGame.wrongAnswer();
			} else {
				aGame.wasCorrectlyAnswered();
			}

			if (aGame.isAnyWinner() != null) {

				Player winner = aGame.isAnyWinner();

				System.out.println(winner.getName() + " won the game!");
				gameInProgress = false;
			}



		} while (gameInProgress);
	}
}
