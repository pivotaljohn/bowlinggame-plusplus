package io.pivotal.la.practice.bowling.scorer_tester;

import io.pivotal.la.practice.bowling.scorer.Game;
import io.pivotal.la.practice.bowling.scorer.GameOverException;
import io.pivotal.la.practice.bowling.scorer.IllegalBowlException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class BowlingScorerTest {

	@Test
	public void theGameBeginsWithAScoreOf0() throws Exception {
		Game game = new Game();
		assertThat(game.score()).isEqualTo(0);
	}

	@Test
	public void theGameBeginsAtFrame1() throws Exception {
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
	public void afterStrike_advanceToTheNextFrame() throws Exception {
		Game game = new Game();
		game.bowl(10);

		assertThat(game.frame()).isEqualTo(2);
	}

	@Test
	public void afterSpare_advanceToTheNextFrame() throws Exception {
		Game game = new Game();
		game.bowl(0);
		game.bowl(10);

		assertThat(game.frame()).isEqualTo(2);
	}

	@Test
	public void afterTwoBowls_evenIfAllThePinsAreNotKnockedDown_advanceToTheNextFrame() throws Exception {
		Game game = new Game();

		game.bowl(1);
		game.bowl(1);

		assertThat(game.frame()).isEqualTo(2);
	}

	@Test
	public void whenFirstBowlOfAFrameIsAStrike_totalScoreIsTheSumOfTheNextTwoBowls() throws Exception {
		Game game = new Game();

		game.bowl(10);
		game.bowl(1);
		game.bowl(1);

		assertThat(game.score()).isEqualTo(14);
	}

	@Test
	public void whenSecondBowlOfAFrameIsASpare_totalScoreIsTheSumOfTheNextBowl() throws Exception {
		Game game = new Game();

		game.bowl(9);
		game.bowl(1);
		game.bowl(1);

		assertThat(game.score()).isEqualTo(12);
	}

	@Test
	public void untilThe10thFrameIsComplete_theGameContinues() throws Exception {
		Game game = new Game();

		for (int frame = 1; frame <= 10; frame++) {
			assertThat(game.isOver()).isFalse();
			game.bowl(0);
			assertThat(game.isOver()).isFalse();
			game.bowl(0);
		}
	}

	@Test
	public void whenThe10thFrameIsComplete_theGameIsOver() throws Exception {
		Game game = new Game();

		for (int frame = 1; frame <= 10; frame++) {
			game.bowl(0);
			game.bowl(0);
		}

		assertThat(game.isOver()).isTrue();
	}

	@Test
	public void inThe10thFrame_whenFirstBowlIsAStrike_allowsAnExtraBowl() throws Exception {
		Game game = new Game();

		for (int frame = 1; frame <= 9; frame++) {
			game.bowl(0);
			game.bowl(0);
		}

		// Frame 10
		game.bowl(10);
		game.bowl(0);
		assertThat(game.isOver()).isFalse();
		game.bowl(0);
		assertThat(game.isOver()).isTrue();
	}

	@Test
	public void inThe10thFrame_whenSecondBowlIsASpare_allowsAnExtraBowl() throws Exception {
		Game game = new Game();

		for (int frame = 1; frame <= 9; frame++) {
			game.bowl(0);
			game.bowl(0);
		}

		// Frame 10
		game.bowl(0);
		game.bowl(10);
		assertThat(game.isOver()).isFalse();
		game.bowl(0);
		assertThat(game.isOver()).isTrue();
	}

	@Test
	public void inThe10thFrame_whenThirdBowlIsMade_theGameIsOver() throws Exception {
		Game game = new Game();

		for (int frame = 1; frame <= 9; frame++) {
			game.bowl(0);
			game.bowl(0);
		}

		// Frame 10
		game.bowl(10);
		game.bowl(10);
		assertThat(game.isOver()).isFalse();
		game.bowl(10);
		assertThat(game.isOver()).isTrue();
	}

	@Test(expected = GameOverException.class)
	public void whenGameIsOver_additionalAttemptsToBowl_areProhibited() throws Exception {
		Game game = new Game();

		for (int frame = 1; frame <= 10; frame++) {
			game.bowl(0);
			game.bowl(0);
		}

		game.bowl(0);
	}

	@Test
	public void aGameWithoutSparesAndStrikes_isProperlyScored() throws Exception {
		Game game = new Game();

		// Frame 1
		game.bowl(1);
		game.bowl(0);

		// Frame 2
		game.bowl(1);
		game.bowl(1);

		// Frame 3
		game.bowl(2);
		game.bowl(1);

		// Frame 4
		game.bowl(2);
		game.bowl(2);

		// Frame 5
		game.bowl(3);
		game.bowl(2);

		// Frame 6
		game.bowl(2);
		game.bowl(4);

		// Frame 7
		game.bowl(7);
		game.bowl(0);

		// Frame 8
		game.bowl(2);
		game.bowl(6);

		// Frame 9
		game.bowl(5);
		game.bowl(4);

		// Frame 10
		game.bowl(8);
		game.bowl(1);

		assertThat(game.score()).isEqualTo(1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 9);
	}

	@Test
	public void aGameWithSparesAndStrikesInFirst9Frames_isProperlyScored() throws Exception {
		Game game = new Game();

		// Frame 1
		game.bowl(1);
		game.bowl(9);

		// Frame 2
		game.bowl(5);
		game.bowl(5);

		// Frame 3
		game.bowl(10);

		// Frame 4
		game.bowl(5);
		game.bowl(5);

		// Frame 5
		game.bowl(0);
		game.bowl(10);

		// Frame 6
		game.bowl(5);
		game.bowl(4);

		// Frame 7
		game.bowl(1);
		game.bowl(1);

		// Frame 8
		game.bowl(2);
		game.bowl(6);

		// Frame 9
		game.bowl(10);

		// Frame 10
		game.bowl(1);
		game.bowl(1);

		assertThat(game.score()).isEqualTo(15 + 20 + 20 + 10 + 15 + 9 + 2 + 8 + 12 + 2);
	}

	@Test
	public void aPerfectGameIs300() throws Exception {
		Game game = new Game();

		for (int frame = 1; frame <= 10; frame++) {
			game.bowl(10);
		}
		game.bowl(10);
		game.bowl(10);

		assertThat(game.score()).isEqualTo(300);
	}
}
