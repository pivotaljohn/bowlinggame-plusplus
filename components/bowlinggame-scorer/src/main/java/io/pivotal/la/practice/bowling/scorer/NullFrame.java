package io.pivotal.la.practice.bowling.scorer;

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
