package io.pivotal.la.practice.bowling.scorer;

public class IllegalBowlException extends Exception {
	private final int pinsStanding;
	private final int pinsClaimedToBeKnockedDown;

	public IllegalBowlException(int pinsStanding, int pinsClaimedToBeKnockedDown) {
		super();
		this.pinsStanding = pinsStanding;
		this.pinsClaimedToBeKnockedDown = pinsClaimedToBeKnockedDown;
	}

	public int getPinsStanding() {
		return pinsStanding;
	}

	public int getPinsClaimedToBeKnockedDown() {
		return pinsClaimedToBeKnockedDown;
	}
}
