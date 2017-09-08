package io.pivotal.la.practice.bowling.scorer;

class NullFrame extends Frame {
	NullFrame() {
		super(null);
		number = 0;
	}

	@Override
	protected void handleAllPinsKnockedDown() {
	}

	@Override
	public Frame nextFrame() {
		return new NthFrame(this);
	}

	@Override
	public int total() {
		return 0;
	}

	@Override
	public boolean isOver() {
		return true;
	}

	@Override
	protected void applyAnyBonuses(int pinsKnockedDown) {
	}
}
