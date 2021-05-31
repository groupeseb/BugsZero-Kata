package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.uglytrivia.enums.QuestionType;

import java.util.*;

public class Game {

	Random rand;

    ArrayList<Player> players = new ArrayList<>();

    protected EnumMap<QuestionType, LinkedList<String>> questions;

    int currentPlayer = 0;

    public  Game(){
    	this.rand = new Random();

		this.questions = new EnumMap<>(QuestionType.class);

		for (int i = 0; i < 50; i++) {
			for (QuestionType questionType : QuestionType.values()) {
				this.questions
						.computeIfAbsent(questionType, (k) -> new LinkedList<>())
						.addLast(questionType.createQuestion(i));
			}
		}
    }

	public boolean isPlayable() {
		return (this.howManyPlayers() >= 2);
	}

	public void add(String playerName) {

		Player player = Player.builder().name(playerName).place(0).purse(0).inPenaltyBox(false).build();
		this.players.add(player);

	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + this.howManyPlayers());
	}

	public int howManyPlayers() {
		return this.players.size();
	}

	/**
	 * Méthode principale de jeu. Représente un tour de jeu pour un joueur.
	 * @return true si le joueur a gagné, false sinon
	 */
	public boolean roll() {
    	int roll = this.rand.nextInt(5) + 1;

		Player player = this.getCurrentPlayer();

		System.out.println(player.getName() + " is the current player");
		System.out.println("They have rolled a " + roll);

		if (player.isInPenaltyBox()) {
			if (roll % 2 != 0) {
				System.out.println(player.getName() + " is getting out of the penalty box");
				this.movePlayerAndAskQuestion(roll);
			} else {
				System.out.println(player.getName() + " is not getting out of the penalty box");
				return false;
			}

		} else {

			this.movePlayerAndAskQuestion(roll);
		}

		this.currentPlayer ++;
		if(this.currentPlayer >= this.players.size()){
			this.currentPlayer = 0;
		}

		return player.doIWin();

	}

	private Player getCurrentPlayer() {
		return this.players.get(this.currentPlayer);
	}

	private void movePlayerAndAskQuestion(int roll) {
		Player player = this.getCurrentPlayer();

		player.move(roll);

		System.out.println(player.getName()
                + "'s new location is "
                + player.getPlace());
		System.out.println("The category is " + this.currentCategory());
		this.askQuestion();

	}

	private void askQuestion() {
    	System.out.println(this.questions.get(this.currentCategory()).removeFirst());

		if (this.rand.nextInt(9) == 7) {
			this.wrongAnswer();
		} else {
			this.wasCorrectlyAnswered();
		}
	}


	private QuestionType currentCategory() {
		Player player = this.getCurrentPlayer();

		if(Arrays.asList(0,4,8).contains(player.getPlace())){
			return QuestionType.POP;
		}

		if(Arrays.asList(1,5,9).contains(player.getPlace())){
			return QuestionType.SCIENCE;
		}

		if(Arrays.asList(2,6,10).contains(player.getPlace())){
			return QuestionType.SPORTS;
		}

		return QuestionType.ROCK;
	}

	public void wasCorrectlyAnswered() {
		Player player = this.getCurrentPlayer();

		System.out.println("Answer was corrent!!!!");
		player.fillPurse();
		System.out.println(player.getName()
				+ " now has "
				+ player.getPurse()
				+ " Gold Coins.");
	}

	public void wrongAnswer(){
		Player player = this.getCurrentPlayer();

		System.out.println("Question was incorrectly answered");
		System.out.println(player.getName() + " was sent to the penalty box");
		player.setInPenaltyBox(true);
	}

}
