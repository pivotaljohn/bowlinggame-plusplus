package io.pivotal.la.practice.bowling.scorer_tester;

import io.pivotal.la.practice.bowling.scorer.Game;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class BowlingScorerTest {

	@Test
	public void score_givenANewGame_returns0() throws Exception {
		Game game = new Game();

		assertThat(game.score()).isEqualTo(0);
	}

	@Test
	public void score_increasesWithEachBowl() throws Exception {
		Game game = new Game();

		game.bowl(5);
		assertThat(game.score()).isEqualTo(5);

		game.bowl(4);
		assertThat(game.score()).isEqualTo(9);
	}
}
