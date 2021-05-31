package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.uglytrivia.enums.QuestionType;
import org.junit.Test;

import java.util.LinkedList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GameTest {

    @Test
    public void constuctor_should_populate_questions_lists() {

        Game game = new Game();

        for(QuestionType type : QuestionType.values()){

            LinkedList<String> questions = game.questions.get(type);

            assertThat(questions.size(), is(50));

            for( int i = 0; i < 50; i++){
                assertThat(questions.get(i), is(type.createQuestion(i)));
            }
        }
    }
}
