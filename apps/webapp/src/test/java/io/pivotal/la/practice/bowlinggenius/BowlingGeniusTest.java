package io.pivotal.la.practice.bowlinggenius;

import com.gargoylesoftware.htmlunit.WebClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BowlingGeniusTest {

	@Autowired
	private WebClient webClient;


	@Before
	public void setUp() throws Exception {
		webClient.getCookieManager().clearCookies();
	}

	@Test
	public void whenTheGameStarts_theScoreIs0() throws Exception {
		HomePage homePage = HomePage.gotoUsing(webClient);
		assertThat(homePage.score()).isEqualTo(0);
	}

	@Test
	public void whenTheGameStarts_theFrameIs1() throws Exception {
		HomePage homePage = HomePage.gotoUsing(webClient);
		assertThat(homePage.frame()).isEqualTo(1);
	}

	@Test
	public void whenABowlIsMade_showsTheUpdatedScore() throws Exception {
		HomePage homePage = HomePage.gotoUsing(webClient)
			.enterPins(5)
			.clickSubmit();

		assertThat(homePage.score()).isEqualTo(5);
	}

	@Test
	public void whenMultipleBowlsAreMade_showsTheUpdatedScore() throws Exception {
		HomePage homePage = HomePage.gotoUsing(webClient)
			.enterPins(5)
			.clickSubmit()
			.enterPins(3)
			.clickSubmit();

		assertThat(homePage.score()).isEqualTo(8);
	}

	@Test
	public void whenPlayerAttemptsToRecordAnIllegalBowl_displaysErrorMessage() throws Exception {
		HomePage homePage = HomePage.gotoUsing(webClient)
			.enterPins(15)
			.clickSubmit();

		assertThat(homePage.statusIsError()).isTrue();
	}

	@Test
	public void givenGameIsOver_whenPlayerAttemptsToBowlAgain_displaysErrorMessage() throws Exception {
		Stream<Integer> tenFrames = IntStream.rangeClosed(1, 10).boxed().sequential();
		HomePage homePage = tenFrames.reduce(
			HomePage.gotoUsing(webClient), this::bowlAFrame, this::returningTheHomePage
		).enterPins(0).clickSubmit();

		assertThat(homePage.statusIsError()).isTrue();
	}

	@Test
	public void asPlayProgresses_showsTheCurrentFrame() throws Exception {
		HomePage homePage = HomePage.gotoUsing(webClient)
			.enterPins(5)
			.clickSubmit()
			.enterPins(3)
			.clickSubmit();

		assertThat(homePage.frame()).isEqualTo(2);
	}

	private HomePage bowlAFrame(HomePage page, Integer frame) {
		try {
			page.enterPins(0).clickSubmit().enterPins(0).clickSubmit();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return page;
	}

	private HomePage returningTheHomePage(HomePage page1, HomePage page2) {
		return page1;
	}
}
