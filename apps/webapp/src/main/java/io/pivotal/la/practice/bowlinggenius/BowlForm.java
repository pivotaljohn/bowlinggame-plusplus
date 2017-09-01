package io.pivotal.la.practice.bowlinggenius;

class BowlForm {
	private Integer pins;

	public Integer getPins() {
		return pins;
	}

	public void setPins(Integer pins) {
		this.pins = pins;
	}

	public void clear() {
		pins = null;
	}
}
