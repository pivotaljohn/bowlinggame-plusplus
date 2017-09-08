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

