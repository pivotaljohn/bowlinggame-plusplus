package io.pivotal.la.practice.bowling.scorer;

public class Game {
	private int score = 0;
	private int pinsStanding = 10;
	private int frame = 1;
	private int bowlsInFrame = 0;
	private Bonus bonus1 = new Bonus();
	private Bonus bonus2 = new Bonus();
	private int extraBowl = 0;

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
		bowlsInFrame++;

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
		if (frame < 10) {
			if (pinsStanding == 0) {
				if (bowlsInFrame == 1) {
					if (bonus1.pendingBonusCount == 0) {
						bonus1.bonus = 0;
						bonus1.pendingBonusCount = 2;
					} else {
						bonus2.bonus = 0;
						bonus2.pendingBonusCount = 2;
					}
					frame++;
					pinsStanding = 10;
					bowlsInFrame = 0;
				} else {
					if (bonus1.pendingBonusCount == 0) {
						bonus1.bonus = 0;
						bonus1.pendingBonusCount = 1;
					} else {
						bonus2.bonus = 0;
						bonus2.pendingBonusCount = 1;
					}
					frame++;
					pinsStanding = 10;
					bowlsInFrame = 0;
				}
			}
			if (bowlsInFrame == 2) {
				frame++;
				pinsStanding = 10;
				bowlsInFrame = 0;
			}
		} else {
			if (pinsStanding == 0) {
				pinsStanding = 10;
				extraBowl = 1;
			}
			if (bowlsInFrame == (2 + extraBowl)) {
				frame++;
				pinsStanding = 10;
				bowlsInFrame = 0;
			}
		}
	}

	public int frame() {
		return frame;
	}

	public boolean isOver() {
		return frame == 11;
	}
}

class Bonus {
	int bonus;
	int pendingBonusCount;
}
