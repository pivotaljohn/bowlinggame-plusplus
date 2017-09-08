package io.pivotal.la.practice.bowling.scorer;

abstract class Frame {
	private Frame previous;
	int bowls = 0;
	int pinsLeft = 10;
	int score = 0;
	int number;

	public static Frame first() {
		return new NthFrame(new NullFrame());
	}

	public Frame next() {
		return number < 9 ? new NthFrame(this) : new TenthFrame(this);
	}

	public Frame(Frame previous) {
		this.previous = previous;
		this.number = previous != null ? previous.number + 1 : 1;
	}

	public int number() {
		return number;
	}

	public int total() {
		return previous.total() + score;
	}

	public int pinsLeft() {
		return pinsLeft;
	}

	public Frame bowl(int pinsKnockedDown) {
		score += pinsKnockedDown;
		pinsLeft -= pinsKnockedDown;
		bowls++;
		previous.applyAnyBonuses(pinsKnockedDown);
		if (pinsLeft == 0) {
			handleAllPinsKnockedDown();
		}
		return isOver() ? next() : this;
	}

	public abstract boolean isOver();

	protected void applyAnyBonuses(int pinsKnockedDown) {
		previous.applyAnyBonuses(pinsKnockedDown);
	}

	protected abstract void handleAllPinsKnockedDown();
}
