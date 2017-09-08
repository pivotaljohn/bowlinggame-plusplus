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
		if (pinsKnockedDown > currentFrame().pinsLeft()) {
			throw new IllegalBowlException(currentFrame().pinsLeft(), pinsKnockedDown);
		}
		currentFrame().bowl(pinsKnockedDown);
		if (currentFrame().isOver()) {
			if (frames.size() == 9) {
				frames.add(new TenthFrame(currentFrame()));
			} else {
				frames.add(new NthFrame(currentFrame()));
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

class NthFrame extends Frame {
	private int pendingBonusCount = 0;
	private int bonus = 0;

	public NthFrame(Frame previous) {
		super(previous);
	}

	@Override
	public boolean isOver() {
		return pinsLeft == 0 || bowls == 2;
	}

	@Override
	protected void applyBonusIfApplicable(int pinsKnockedDown) {
		super.applyBonusIfApplicable(pinsKnockedDown);
		if (pendingBonusCount > 0) {
			bonus += pinsKnockedDown;
			pendingBonusCount--;
			if (pendingBonusCount == 0) {
				score += bonus;
			}
		}
	}

	@Override
	protected void handleAllPinsKnockedDown() {
		pendingBonusCount = 3 - bowls;
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
	protected void handleAllPinsKnockedDown() {
	}

	@Override
	public boolean isOver() {
		return true;
	}

	@Override
	protected void applyBonusIfApplicable(int pinsKnockedDown) {
	}
}
