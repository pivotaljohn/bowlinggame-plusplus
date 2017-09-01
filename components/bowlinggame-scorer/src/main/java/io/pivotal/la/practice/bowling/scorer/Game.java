package io.pivotal.la.practice.bowling.scorer;

public class Game {
	private int score = 0;
	public int score() {
		return score;
	}

	public void bowl(int pinsKnockedDown) {
		score += pinsKnockedDown;
	}
}
