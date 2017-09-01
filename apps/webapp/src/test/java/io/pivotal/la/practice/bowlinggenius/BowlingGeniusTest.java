package io.pivotal.la.practice.bowlinggenius;

import com.gargoylesoftware.htmlunit.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest
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
	public void whenABowlIsMade_theScoreIsUpdated() throws Exception {
		HomePage homePage = HomePage.gotoUsing(webClient)
			.enterPins(5)
			.clickSubmit();

		assertThat(homePage.score()).isEqualTo(5);
	}

	@Test
	public void whenMultipleBowlsAreMade_theScoreAccumulates() throws Exception {
		HomePage homePage = HomePage.gotoUsing(webClient)
			.enterPins(5)
			.clickSubmit()
			.enterPins(3)
			.clickSubmit();

		assertThat(homePage.score()).isEqualTo(8);
	}
}
