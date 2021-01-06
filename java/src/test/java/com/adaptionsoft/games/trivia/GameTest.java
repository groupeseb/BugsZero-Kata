package com.adaptionsoft.games.trivia;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.stream.IntStream;

import org.approvaltests.Approvals;
import org.junit.Test;

import com.adaptionsoft.games.trivia.runner.GameRunner;

public class GameTest {

	@Test
	public void itsLockedDown() throws Exception {

		Random randomizer = new Random(123455); // lock seed to have identical results every time.
		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(resultStream));

		IntStream.range(1, 15).forEach(i -> GameRunner.playGame(randomizer));

		Approvals.verify(resultStream.toString());

	}
}
