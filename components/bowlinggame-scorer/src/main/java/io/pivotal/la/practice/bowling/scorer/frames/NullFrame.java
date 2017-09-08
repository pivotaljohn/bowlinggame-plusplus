package io.pivotal.la.practice.bowling.scorer.frames;

class NullFrame extends Frame {
	NullFrame() {
		super(null);
		number = 0;
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

	@Override
	protected void handleAllPinsKnockedDown() {
	}
}
