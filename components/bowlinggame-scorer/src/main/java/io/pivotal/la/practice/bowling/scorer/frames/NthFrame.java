package io.pivotal.la.practice.bowling.scorer.frames;

class NthFrame extends Frame {
	private int pendingBonuses = 0;
	private int bonus = 0;

	NthFrame(Frame previous) {
		super(previous);
	}

	@Override
	public Frame nextFrame() {
		return number < 9 ? new NthFrame(this) : new TenthFrame(this);
	}

	@Override
	public boolean isOver() {
		return pinsLeft == 0 || bowls == 2;
	}

	@Override
	protected void applyAnyBonuses(int pinsKnockedDown) {
		super.applyAnyBonuses(pinsKnockedDown);
		if (pendingBonuses > 0) {
			bonus += pinsKnockedDown;
			pendingBonuses--;
			if (pendingBonuses == 0) {
				score += bonus;
			}
		}
	}

	@Override
	protected void handleAllPinsKnockedDown() {
		pendingBonuses = 1 + (2 - bowls);
	}
}
