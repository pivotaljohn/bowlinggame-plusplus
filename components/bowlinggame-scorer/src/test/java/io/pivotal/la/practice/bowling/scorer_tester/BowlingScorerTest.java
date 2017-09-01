package io.pivotal.la.practice.bowling.scorer_tester;

import io.pivotal.la.practice.bowling.scorer.Game;
import io.pivotal.la.practice.bowling.scorer.IllegalBowlException;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class BowlingScorerTest {

	@Test
	public void theGameBeginsWithAScoreOf0() throws Exception {
		Game game = new Game();
		assertThat(game.score()).isEqualTo(0);
	}

	@Test
	public void theGameBeginsWithFrame1() throws Exception {
		Game game = new Game();
		assertThat(game.frame()).isEqualTo(1);
	}

	@Test
	public void theScoreIsTheSumOfAllPinsKnockedDown() throws Exception {
		Game game = new Game();

		game.bowl(5);
		assertThat(game.score()).isEqualTo(5);

		game.bowl(4);
		assertThat(game.score()).isEqualTo(9);
	}


	@Test(expected = IllegalBowlException.class)
	public void attemptingToRecordMorePinsKnockedDownThanPossible_isProhibited() throws Exception {
		Game game = new Game();
		game.bowl(9);
		game.bowl(5);
	}

	@Test
	public void afterTwoBowls_pinsAreRest() throws Exception {
		Game game = new Game();
		game.bowl(8);
		game.bowl(1);
		game.bowl(8);
	}

	@Test
	public void evenIfAllThePinsAreNotKnockedDown_afterTwoBowls_advanceToTheNextFrame() throws Exception {
		Game game = new Game();

		game.bowl(1);
		game.bowl(1);

		assertThat(game.frame()).isEqualTo(2);

	}
}
