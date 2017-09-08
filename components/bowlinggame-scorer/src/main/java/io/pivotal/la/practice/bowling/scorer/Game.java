package io.pivotal.la.practice.bowling.scorer;

import java.util.ArrayList;
import java.util.List;

public class Game {
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

		if (frames.size() < 10) {
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
	private int score = 0;
	private int pendingBonusCount = 0;
	private int bonus = 0;
	private Frame previous;

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
		previous.applyBonusIfApplicable(pinsKnockedDown);
		if (pinsLeft == 0) {
			if (bowls == 1) {
				bonus = 0;
				pendingBonusCount = 2;
			} else {
				bonus = 0;
				pendingBonusCount = 1;
			}
		}
	}

	protected void applyBonusIfApplicable(int pinsKnockedDown) {
		if (pendingBonusCount > 0) {
			bonus += pinsKnockedDown;
			pendingBonusCount--;
			if (pendingBonusCount == 0) {
				score += bonus;
			}
		}
		previous.applyBonusIfApplicable(pinsKnockedDown);
	}
}

class NullFrame extends Frame {
	public NullFrame() {
		super(null);
	}

	@Override
	protected void applyBonusIfApplicable(int pinsKnockedDown) {
	}
}

class Bonus {
	int bonus;
	int pendingBonusCount;
}
