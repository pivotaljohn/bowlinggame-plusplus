package io.pivotal.la.practice.bowling.scorer;

class TenthFrame extends Frame {
	private int extraBowl = 0;

	public TenthFrame(Frame previous) {
		super(previous);
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
