package io.pivotal.la.practice.bowling.scorer;

abstract class Frame {
	private Frame previous;
	int bowls = 0;
	int pinsLeft = 10;
	int score = 0;

	public static Frame first() {
		return new NthFrame(new NullFrame());
	}

	public Frame(Frame previous) {
		this.previous = previous;
	}

	public int score() {
		return score;
	}

	public int pinsLeft() {
		return pinsLeft;
	}

	public void bowl(int pinsKnockedDown) {
		score += pinsKnockedDown;
		pinsLeft -= pinsKnockedDown;
		bowls++;
		previous.applyBonusIfApplicable(pinsKnockedDown);
		if (pinsLeft == 0) {
			handleAllPinsKnockedDown();
		}
	}

	public abstract boolean isOver();

	protected void applyBonusIfApplicable(int pinsKnockedDown) {
		previous.applyBonusIfApplicable(pinsKnockedDown);
	}

	protected abstract void handleAllPinsKnockedDown();
}
