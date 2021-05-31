package com.adaptionsoft.games.uglytrivia.enums;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class QuestionTypeTest {

    @Test
    public void createQuestion_given_index_should_return_question() {

        int i = 24;

        assertThat(QuestionType.POP.createQuestion(i), is("Pop Question 24"));
    }
}
