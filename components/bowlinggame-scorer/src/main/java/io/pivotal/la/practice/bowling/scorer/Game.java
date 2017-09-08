package io.pivotal.la.practice.bowling.scorer;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private List<Frame> frames;

	public Game() {
		frames = new ArrayList<>();
		frames.add(Frame.first());
	}

	public int score() {
		return frames.stream().mapToInt(Frame::score).sum();
	}

	public void bowl(int pinsKnockedDown) throws IllegalBowlException, GameOverException {
		if (isOver()) {
			throw new GameOverException();
		}
		if (pinsKnockedDown > currentFrame().pinsLeft) {
			throw new IllegalBowlException(currentFrame().pinsLeft, pinsKnockedDown);
		}
		currentFrame().bowl(pinsKnockedDown);
		if (currentFrame().isOver()) {
			if (frames.size() == 9) {
				frames.add(new TenthFrame(currentFrame()));
			} else {
				frames.add(new Frame(currentFrame()));
			}
		}
	}

	public int frame() {
		return frames.size();
	}

	public boolean isOver() {
		return frames.size() == 11;
	}

	private Frame currentFrame() {
		return frames.get(frames.size() - 1);
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
			handleAllPinsKnockedDown();
		}
	}

	protected void handleAllPinsKnockedDown() {
		pendingBonusCount = 3 - bowls;
	}

	public boolean isOver() {
		return pinsLeft == 0 || bowls == 2;
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

class TenthFrame extends Frame {
	private int extraBowl = 0;

	public TenthFrame(Frame previous) {
		super(previous);
	}

	@Override
	public boolean isOver() {
		return bowls == 2 + extraBowl;
	}

	@Override
	protected void handleAllPinsKnockedDown() {
		pinsLeft = 10;
		extraBowl = 1;
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
