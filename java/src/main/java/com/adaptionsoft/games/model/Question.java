package com.adaptionsoft.games.model;

import com.adaptionsoft.games.enums.QuestionCategoryEnum;


public class Question {
  protected String label;
  protected QuestionCategoryEnum category;

  public Question(String label, QuestionCategoryEnum category) {
    this.label = label;
    this.category = category;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public QuestionCategoryEnum getCategory() {
    return category;
  }

  public void setCategory(QuestionCategoryEnum category) {
    this.category = category;
  }
}
