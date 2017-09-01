package io.pivotal.la.practice.bowling.scorer;

public class Game {
	private int score = 0;
	private int pinsStanding = 10;
	private int frame = 1;
	private int bowlsInFrame = 0;

	public int score() {
		return score;
	}

	public void bowl(int pinsKnockedDown) throws IllegalBowlException {
		if(pinsKnockedDown > pinsStanding) {
			throw new IllegalBowlException();
		}
		score += pinsKnockedDown;
		pinsStanding -= pinsKnockedDown;
		bowlsInFrame++;
		if(bowlsInFrame == 2) {
			frame++;
			pinsStanding = 10;
			bowlsInFrame = 0;
		}
	}

	public int frame() {
		return frame;
	}
}
