package com.adaptionsoft.games.uglytrivia.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum QuestionType {

    POP("Pop"),
    SCIENCE("Science"),
    SPORTS("Sports"),
    ROCK("Rock");

    private final String name;

    public String createQuestion(int index){
        return this.name + " Question " + index;
    }
}
