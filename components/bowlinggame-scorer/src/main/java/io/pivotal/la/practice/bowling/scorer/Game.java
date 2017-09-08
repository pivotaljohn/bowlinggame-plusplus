package io.pivotal.la.practice.bowling.scorer;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private int extraBowl = 0;
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
		if (pinsKnockedDown > getCurrentFrame().pinsLeft) {
			throw new IllegalBowlException(getCurrentFrame().pinsLeft, pinsKnockedDown);
		}
		getCurrentFrame().bowl(pinsKnockedDown);

		if (frames.size() < 10) {
			if (getCurrentFrame().isOver()) {
				frames.add(new Frame(getCurrentFrame()));
			}
		} else {
			if (getCurrentFrame().pinsLeft == 0) {
				getCurrentFrame().pinsLeft = 10;
				extraBowl = 1;
			}
			if (getCurrentFrame().bowls == (2 + extraBowl)) {
				frames.add(new Frame(getCurrentFrame()));
			}
		}
	}

	public int frame() {
		return frames.size();
	}

	public boolean isOver() {
		return frames.size() == 11;
	}

	private Frame getCurrentFrame() {
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
			pendingBonusCount = 3 - bowls;
		}
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

class NullFrame extends Frame {
	public NullFrame() {
		super(null);
	}

	@Override
	protected void applyBonusIfApplicable(int pinsKnockedDown) {
	}
}
