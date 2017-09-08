package io.pivotal.la.practice.bowling.scorer.frames;

public abstract class Frame {
	private Frame previous;
	int bowls;
	int pinsLeft;
	int score;
	int number;

	public static Frame first() {
		return new NullFrame().nextFrame();
	}

	public Frame(Frame previous) {
		this.previous = previous;
		this.bowls = 0;
		this.pinsLeft = 10;
		this.score = 0;
		this.number = previous != null ? previous.number + 1 : 1;
	}

	public abstract Frame nextFrame();

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
		return isOver() ? nextFrame() : this;
	}

	public abstract boolean isOver();

	protected void applyAnyBonuses(int pinsKnockedDown) {
		previous.applyAnyBonuses(pinsKnockedDown);
	}

	protected abstract void handleAllPinsKnockedDown();
}
