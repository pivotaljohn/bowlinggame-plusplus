package io.pivotal.la.practice.bowlinggenius;

class GameStatus {
	private int score;
	private int frame;
	private String status;

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean getHasError() {
		return status != null && !status.isEmpty();
	}
}
