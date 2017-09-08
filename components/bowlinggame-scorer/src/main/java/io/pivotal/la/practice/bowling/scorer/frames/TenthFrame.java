package io.pivotal.la.practice.bowling.scorer.frames;

class TenthFrame extends Frame {
	private int extraBowl = 0;

	TenthFrame(Frame previous) {
		super(previous);
	}

	@Override
	public Frame nextFrame() {
		return this;
	}

	@Override
	public boolean isOver() {
		return bowls == 2 + extraBowl;
	}

	@Override
	protected void handleAllPinsKnockedDown() {
		pinsLeft = 10;
		extraBowl = 1;
	}
}
