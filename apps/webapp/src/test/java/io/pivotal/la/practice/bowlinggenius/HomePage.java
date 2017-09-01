package io.pivotal.la.practice.bowlinggenius;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;

public class HomePage {
	private HtmlPage page;

	private HtmlElement scoreLabel;
	private HtmlElement score;
	private HtmlNumberInput pins;
	private HtmlSubmitInput button;

	private HomePage(WebClient webClient) throws IOException {
		bindDOMElementsToDataMembers(webClient.getPage("/"));
	}

	private HomePage(HtmlPage page) {
		bindDOMElementsToDataMembers(page);
	}

	private void bindDOMElementsToDataMembers(HtmlPage page) {
		this.page = page;
		scoreLabel = this.page.getHtmlElementById("score-label");
		score = this.page.getHtmlElementById("score");
		pins = this.page.getHtmlElementById("pins");
		button = this.page.getHtmlElementById("bowl-button");
	}

	public static HomePage gotoUsing(WebClient webClient) throws IOException {
		return new HomePage(webClient);
	}

	public String scoreLabel() {
		return scoreLabel.getTextContent();
	}

	public int score() {
		return Integer.parseInt(score.getTextContent());
	}

	public HomePage enterPins(int input) {
		pins.setText("" + input);
		return this;
	}

	public HomePage clickSubmit() throws IOException {
		return new HomePage((HtmlPage) button.click());
	}
}
