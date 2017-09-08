package io.pivotal.la.practice.bowling.scorer;

import io.pivotal.la.practice.bowling.scorer.frames.Frame;

public class Game {
	private Frame currentFrame;

	public Game() {
		currentFrame = Frame.first();
	}

	public int score() {
		return currentFrame.total();
	}

	public void bowl(int pinsKnockedDown) throws IllegalBowlException, GameOverException {
		if (isOver()) {
			throw new GameOverException();
		}
		if (pinsKnockedDown > currentFrame.pinsLeft()) {
			throw new IllegalBowlException(currentFrame.pinsLeft(), pinsKnockedDown);
		}
		currentFrame = currentFrame.bowl(pinsKnockedDown);
	}

	public int frame() {
		return currentFrame.number();
	}

	public boolean isOver() {
		return currentFrame.isOver();
	}
}

