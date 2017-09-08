package io.pivotal.la.practice.bowling.scorer;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private int score = 0;
	private int pinsStanding = 10;
	private int frame = 1;
	private Bonus bonus1 = new Bonus();
	private Bonus bonus2 = new Bonus();
	private int extraBowl = 0;
	private List<Frame> frames;
	private Frame currentFrame;

	public Game() {
		frames = new ArrayList<>();
		currentFrame = new Frame();
		frames.add(currentFrame);
	}

	public int score() {
		return score;
	}

	public void bowl(int pinsKnockedDownThisBowl) throws IllegalBowlException, GameOverException {
		if (isOver()) {
			throw new GameOverException();
		}
		if (pinsKnockedDownThisBowl > pinsStanding) {
			throw new IllegalBowlException(pinsStanding, pinsKnockedDownThisBowl);
		}
		score += pinsKnockedDownThisBowl;
		pinsStanding -= pinsKnockedDownThisBowl;
		currentFrame.bowls++;

		if (bonus1.pendingBonusCount > 0) {
			bonus1.bonus += pinsKnockedDownThisBowl;
			bonus1.pendingBonusCount--;
			if (bonus1.pendingBonusCount == 0) {
				score += bonus1.bonus;
			}
		}
		if (bonus2.pendingBonusCount > 0) {
			bonus2.bonus += pinsKnockedDownThisBowl;
			bonus2.pendingBonusCount--;
			if (bonus2.pendingBonusCount == 0) {
				score += bonus2.bonus;
			}
		}
		if (frames.size() < 10) {
			if (pinsStanding == 0) {
				if (currentFrame.bowls == 1) {
					if (bonus1.pendingBonusCount == 0) {
						bonus1.bonus = 0;
						bonus1.pendingBonusCount = 2;
					} else {
						bonus2.bonus = 0;
						bonus2.pendingBonusCount = 2;
					}
					currentFrame = new Frame();
					frames.add(currentFrame);
					pinsStanding = 10;
				} else {
					if (bonus1.pendingBonusCount == 0) {
						bonus1.bonus = 0;
						bonus1.pendingBonusCount = 1;
					} else {
						bonus2.bonus = 0;
						bonus2.pendingBonusCount = 1;
					}
					currentFrame = new Frame();
					frames.add(currentFrame);
					pinsStanding = 10;
				}
			}
			if (currentFrame.bowls == 2) {
				currentFrame = new Frame();
				frames.add(currentFrame);
				pinsStanding = 10;
			}
		} else {
			if (pinsStanding == 0) {
				pinsStanding = 10;
				extraBowl = 1;
			}
			if (currentFrame.bowls == (2 + extraBowl)) {
				currentFrame = new Frame();
				frames.add(currentFrame);
				pinsStanding = 10;
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
}

class Bonus {
	int bonus;
	int pendingBonusCount;
}
