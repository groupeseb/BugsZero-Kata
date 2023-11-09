package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.enums.QuestionCategoryEnum;
import com.adaptionsoft.games.model.Player;
import com.adaptionsoft.games.model.Question;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.adaptionsoft.games.enums.QuestionCategoryEnum.*;

public class Game {
  protected List<Player> players;
  protected LinkedList<Question> questions;
  protected Player currentPlayer;
  boolean isGettingOutOfPenaltyBox;
  private ListIterator<Player> playerListIterator = null;

  // Singleton ?
  public Game(String... players) {
    this.players = Arrays.stream(players).map(Player::new).collect(Collectors.toList());
    this.initQuestions();
    // On récupère le premier joueur.
    nextPlayer();
  }


  void initQuestions() {
    List<Question> popQuestions = IntStream.range(0, 50).mapToObj(index -> new Question(String.format("Pop Question %d", index), POP)).collect(Collectors.toList());
    List<Question> scienceQuestions = IntStream.range(0, 50).mapToObj(index -> new Question(String.format("Science Question %d", index), SCIENCE)).collect(Collectors.toList());
    List<Question> sportsQuestions = IntStream.range(0, 50).mapToObj(index -> new Question(String.format("Sports Question %d", index), SPORTS)).collect(Collectors.toList());
    List<Question> rockQuestions = IntStream.range(0, 50).mapToObj(index -> new Question(String.format("Rock Question %d", index), ROCK)).collect(Collectors.toList());
    questions = new LinkedList<Question>() {{
      addAll(popQuestions);
      addAll(scienceQuestions);
      addAll(sportsQuestions);
      addAll(rockQuestions);
    }};
  }

  // En l'état, le jeu peut démarrer alors qu'il n'y a qu'un seul joueur.
  // A ajouter dans les conditions de démarrage du jeu
  public boolean isPlayable() {
    return (howManyPlayers() >= 2);
  }

  public int howManyPlayers() {
    return players.size();
  }

  public void nextPlayer() {
    // On reboucle sur les utilisateurs si tous les joueurs ont joué
    if (playerListIterator == null || !playerListIterator.hasNext()) {
      playerListIterator = this.players.listIterator();
    }
    currentPlayer = this.playerListIterator.next();
  }

  public void roll(int roll) {

    System.out.println(String.format("%s is the current player", currentPlayer.getName()));
    System.out.println(String.format("They have rolled a %d", roll));

    isGettingOutOfPenaltyBox = currentPlayer.isInPenaltyBox() && roll % 2 != 0;
    if (!currentPlayer.isInPenaltyBox() || isGettingOutOfPenaltyBox) {
      movePlayerAndAskQuestion(roll);
    }
    if (currentPlayer.isInPenaltyBox()) {
      System.out.println(String.format(roll % 2 != 0 ? "%s is getting out of the penalty box" : "%s is not getting out of the penalty box", player.getName()));
    }
  }

  // TODO A refacto - L'affichage et le traitement n'ont rien à faire ensemble
  private void movePlayerAndAskQuestion(int roll) {
    currentPlayer.moveForward(roll);
    currentPlayer.displayNewPlace();
    System.out.println("The category is " + currentCategory());
    askQuestion();
  }

  // TODO Ajouter une custom exception non controlée pour mieux spécifier l'erreur
  private void askQuestion() {
    List<Question> categoryQuestions = questions.stream().filter(item -> currentCategory().equals(item.getCategory())).collect(Collectors.toList());
    Optional<Question> optionalQuestion = categoryQuestions.stream().findFirst();
    Question question = optionalQuestion.orElseThrow(RuntimeException::new);
    // Pourquoi supprimer les questions ? Et si on utilise plus de questions que les 50 questions prévues
    this.questions.remove(question);
    System.out.println(question.getLabel());
  }


  // TODO Add unit tests
  private QuestionCategoryEnum currentCategory() {
    QuestionCategoryEnum category;
    switch (currentPlayer.getPlace()) {
      case 0:
      case 8:
      case 4:
        category = QuestionCategoryEnum.POP;
        break;
      case 1:
      case 5:
      case 9:
        category = QuestionCategoryEnum.SCIENCE;
        break;
      case 2:
      case 6:
      case 10:
        category = QuestionCategoryEnum.SPORTS;
        break;
      default:
        category = QuestionCategoryEnum.ROCK;
    }
    return category;
  }

  // Refacto à finir et à optimiser
  // didPlayerWin et nextPlayer doit être appelé à la fin de chaque tour
  public boolean wasCorrectlyAnswered() {
    if (currentPlayer.isInPenaltyBox()) {
      if (isGettingOutOfPenaltyBox) {
        System.out.println("Answer was correct!!!!");
        currentPlayer.incrementPurse();
        System.out.println(String.format("%s now has %d Gold Coins.", currentPlayer.getName(), currentPlayer.getPurce()));
        nextPlayer();

        boolean winner = didPlayerWin();

        return winner;
      } else {
        nextPlayer();
        return true;
      }


    } else {

      System.out.println("Answer was corrent!!!!");
      purses[currentPlayer]++;
      System.out.println(players.get(currentPlayer)
          + " now has "
          + purses[currentPlayer]
          + " Gold Coins.");
      boolean winner = didPlayerWin();
      nextPlayer();

      return winner;
    }
    nextPlayer();
  }


  public boolean wrongAnswer() {
    System.out.println("Question was incorrectly answered");
    System.out.println(currentPlayer.getName() + " was sent to the penalty box");
    currentPlayer.setInPenaltyBox(true);

    nextPlayer();
    return true;
  }

  // Retourne false quand l'utilisateur a gagné
  // A renommer ?
  private boolean didPlayerWin() {
    return !(currentPlayer.getPurce() == 6);
  }
}
