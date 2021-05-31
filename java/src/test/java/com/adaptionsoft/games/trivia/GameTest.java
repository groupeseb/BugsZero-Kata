package com.adaptionsoft.games.trivia;


import com.adaptionsoft.games.trivia.runner.GameRunner;
import org.approvaltests.Approvals;
import org.junit.Test;

import java.io.*;
import java.util.Random;
import java.util.stream.IntStream;

public class GameTest {

    // C'est quoi ce test ?
	@Test
	public void itsLockedDown() {

        Random randomizer = new Random(123455);
        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(resultStream));

        IntStream.range(1,15).forEach(i -> GameRunner.playGame(randomizer));

        Approvals.verify(resultStream.toString());

	}
}
