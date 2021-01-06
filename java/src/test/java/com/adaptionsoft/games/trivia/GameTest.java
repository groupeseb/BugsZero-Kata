package com.adaptionsoft.games.trivia;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.stream.IntStream;

//TODO (AQS): Déplacer ApprovalTest pour le définir en tant que dépendance maven
import org.approvaltests.Approvals;
import org.junit.Test;

import com.adaptionsoft.games.trivia.runner.GameRunner;

public class GameTest {

	@Test
	public void itsLockedDown() throws Exception {

		final Random randomizer = new Random(123455);
		final ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(resultStream));

		IntStream.range(1,15).forEach(i -> GameRunner.playGame(randomizer));

		Approvals.verify(resultStream.toString());

	}
}
