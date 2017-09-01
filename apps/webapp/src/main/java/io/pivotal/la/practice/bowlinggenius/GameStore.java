package io.pivotal.la.practice.bowlinggenius;

import io.pivotal.la.practice.bowling.scorer.Game;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
class GameStore {
	private Game game;

	Game getCurrentGame() {
		if(game == null) {
			game = new Game();
		}
		return game;
	}
}
