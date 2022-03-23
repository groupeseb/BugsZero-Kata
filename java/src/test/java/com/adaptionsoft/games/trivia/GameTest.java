package com.adaptionsoft.games.trivia;

//Eviter l'utilisation de la wildcard dans les imports
import static org.junit.Assert.*;

import com.adaptionsoft.games.trivia.runner.GameRunner;
import org.approvaltests.Approvals;
import org.junit.Test;

import java.io.*;
import java.util.Random;
import java.util.stream.IntStream;

/*
Classe de test très pauvre et non unitaire, il faudra créer des calsses de test permettant de tester unitairement chaque fonctions du code
 */
public class GameTest {

	@Test
	public void itsLockedDown() throws Exception {

        Random randomizer = new Random(123455);
        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(resultStream));

        IntStream.range(1,15).forEach(i -> GameRunner.playGame(randomizer));

        Approvals.verify(resultStream.toString());

	}
}
