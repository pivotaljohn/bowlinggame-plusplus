package io.pivotal.la.practice.bowlinggenius;

import io.pivotal.la.practice.bowling.scorer.Game;

class GameStatusTransformer {
	GameStatus statusFrom(Game game) {
		GameStatus gameStatus = new GameStatus();
		gameStatus.setFrame(game.frame());
		gameStatus.setScore(game.score());
		return gameStatus;
	}
}
