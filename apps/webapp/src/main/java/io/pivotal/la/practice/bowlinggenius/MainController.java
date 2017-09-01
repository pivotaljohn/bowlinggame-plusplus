package io.pivotal.la.practice.bowlinggenius;

import io.pivotal.la.practice.bowling.scorer.Game;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {
	// TODO: enable synchronizeOnSession so that session access is thread-safe.
	// TODO: o.s.validation.Errors / o.s.v.BindingResult for catching errors.

	@GetMapping("/")
	public ModelAndView index() {
		Map<String, Integer> model = new HashMap<>();
		model.put("score", 0);
		return new ModelAndView("index", model);
	}

	@PostMapping("/")
	public ModelAndView recordBowl(@RequestParam("pins") int pins, HttpSession session) {
		Game game = (Game) session.getAttribute("game");
		if(game == null) {
			game = new Game();
		}
		game.bowl(pins);
		session.setAttribute("game", game);
		Map<String, Integer> model = new HashMap<>();
		model.put("score", game.score());
		return new ModelAndView("index", model);
	}
}
