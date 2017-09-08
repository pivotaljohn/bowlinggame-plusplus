package io.pivotal.la.practice.bowling.scorer;

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
	protected void applyAnyBonuses(int pinsKnockedDown) {
		super.applyAnyBonuses(pinsKnockedDown);
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
