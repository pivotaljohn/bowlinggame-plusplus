package io.pivotal.la.practice.bowling.scorer;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private Bonus bonus1 = new Bonus();
	private Bonus bonus2 = new Bonus();
	private int extraBowl = 0;
	private List<Frame> frames;
	private Frame currentFrame;

	public Game() {
		frames = new ArrayList<>();
		currentFrame = Frame.first();
		frames.add(currentFrame);
	}

	public int score() {
		return frames.stream().mapToInt(Frame::score).sum();
	}

	public void bowl(int pinsKnockedDown) throws IllegalBowlException, GameOverException {
		if (isOver()) {
			throw new GameOverException();
		}
		if (pinsKnockedDown > currentFrame.pinsLeft) {
			throw new IllegalBowlException(currentFrame.pinsLeft, pinsKnockedDown);
		}
		currentFrame.bowl(pinsKnockedDown);

		if (bonus1.pendingBonusCount > 0) {
			bonus1.bonus += pinsKnockedDown;
			bonus1.pendingBonusCount--;
			if (bonus1.pendingBonusCount == 0) {
				currentFrame.score += bonus1.bonus;
			}
		}
		if (bonus2.pendingBonusCount > 0) {
			bonus2.bonus += pinsKnockedDown;
			bonus2.pendingBonusCount--;
			if (bonus2.pendingBonusCount == 0) {
				currentFrame.score += bonus2.bonus;
			}
		}
		if (frames.size() < 10) {
			if (currentFrame.pinsLeft == 0) {
				if (currentFrame.bowls == 1) {
					if (bonus1.pendingBonusCount == 0) {
						bonus1.bonus = 0;
						bonus1.pendingBonusCount = 2;
					} else {
						bonus2.bonus = 0;
						bonus2.pendingBonusCount = 2;
					}
				} else {
					if (bonus1.pendingBonusCount == 0) {
						bonus1.bonus = 0;
						bonus1.pendingBonusCount = 1;
					} else {
						bonus2.bonus = 0;
						bonus2.pendingBonusCount = 1;
					}
				}
			}
			if (currentFrame.pinsLeft == 0 || currentFrame.bowls == 2) {
				currentFrame = new Frame(currentFrame);
				frames.add(currentFrame);
			}
		} else {
			if (currentFrame.pinsLeft == 0) {
				currentFrame.pinsLeft = 10;
				extraBowl = 1;
			}
			if (currentFrame.bowls == (2 + extraBowl)) {
				currentFrame = new Frame(currentFrame);
				frames.add(currentFrame);
			}
		}
	}

	public int frame() {
		return frames.size();
	}

	public boolean isOver() {
		return frames.size() == 11;
	}
}

class Frame {
	int bowls = 0;
	int pinsLeft = 10;
	int score = 0;
	int pendingBonusCount = 0;
	int bonus = 0;
	Frame previous;

	public static Frame first() {
		return new Frame(new NullFrame());
	}

	public Frame(Frame previous) {
		this.previous = previous;
	}

	public int score() {
		return score;
	}

	public void bowl(int pinsKnockedDown) {
		score += pinsKnockedDown;
		pinsLeft -= pinsKnockedDown;
		bowls++;
	}
}

class NullFrame extends Frame {
	public NullFrame() {
		super(null);
	}
}

class Bonus {
	int bonus;
	int pendingBonusCount;
}
