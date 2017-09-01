package io.pivotal.la.practice.bowlinggenius;

import io.pivotal.la.practice.bowling.scorer.Game;
import io.pivotal.la.practice.bowling.scorer.IllegalBowlException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
class MainController {
	// TODO: enable synchronizeOnSession so that session access is thread-safe.
	// TODO: o.s.validation.Errors / o.s.v.BindingResult for catching errors.

	private final GameStore gameStore;

	public MainController(GameStore gameStore) {
		this.gameStore = gameStore;
	}

	@GetMapping("/")
	public ModelAndView index() {
		Game game = gameStore.getCurrentGame();
		return new ModelAndView("index", modelFrom(game));
	}

	@PostMapping("/")
	public ModelAndView recordBowl(@RequestParam("pins") int pins) {
		Game game = gameStore.getCurrentGame();
		try {
			game.bowl(pins);
		} catch (IllegalBowlException e) {
			throw new RuntimeException(e);
		}
		return new ModelAndView("index", modelFrom(game));
	}


	private Map<String, Integer> modelFrom(Game game) {
		Map<String, Integer> model = new HashMap<>();
		model.put("score", game.score());
		model.put("frame", game.frame());
		return model;
	}
}
