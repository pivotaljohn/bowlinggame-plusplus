package io.pivotal.la.practice.bowlinggenius;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlNumberInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.StreamSupport;

class HomePage {
	private HtmlPage page;

	private HtmlElement status;
	private HtmlElement frame;
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
		status = this.page.getHtmlElementById("status");
		frame = this.page.getHtmlElementById("frame");
		score = this.page.getHtmlElementById("score");
		pins = this.page.getHtmlElementById("pins");
		button = this.page.getHtmlElementById("bowl-button");
	}

	public static HomePage gotoUsing(WebClient webClient) throws IOException {
		return new HomePage(webClient);
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

	public int frame() {
		return Integer.parseInt(frame.getTextContent());
	}

	public boolean statusIsError() {
		return StreamSupport.stream(status.getHtmlElementDescendants().spliterator(), false)
			.anyMatch(he ->
				Objects.equals(he.getAttribute("class"), "error"));
	}
}
