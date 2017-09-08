package io.pivotal.la.practice.bowling.scorer;

public class Game {
	private int score = 0;
	private int pinsStanding = 10;
	private int frame = 1;
	private int bowlsInFrame = 0;
	private int bonus1 = 0;
	private int pendingBonus1Count = 0;
	private int bonus2 = 0;
	private int pendingBonus2Count = 0;
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

		if (pendingBonus1Count > 0) {
			bonus1 += pinsKnockedDownThisBowl;
			pendingBonus1Count--;
			if (pendingBonus1Count == 0) {
				score += bonus1;
			}
		}
		if (pendingBonus2Count > 0) {
			bonus2 += pinsKnockedDownThisBowl;
			pendingBonus2Count--;
			if (pendingBonus2Count == 0) {
				score += bonus2;
			}
		}
		if (frame < 10) {
			if (pinsStanding == 0) {
				if (bowlsInFrame == 1) {
					if (pendingBonus1Count == 0) {
						bonus1 = 0;
						pendingBonus1Count = 2;
					} else {
						bonus2 = 0;
						pendingBonus2Count = 2;
					}
					frame++;
					pinsStanding = 10;
					bowlsInFrame = 0;
				} else {
					if (pendingBonus1Count == 0) {
						bonus1 = 0;
						pendingBonus1Count = 1;
					} else {
						bonus2 = 0;
						pendingBonus2Count = 1;
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
