package io.pivotal.la.practice.bowlinggenius;

import io.pivotal.la.practice.bowling.scorer.Game;
import io.pivotal.la.practice.bowling.scorer.IllegalBowlException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static java.lang.String.*;

@Controller
@RequestMapping("/")
class MainController {
	// TODO: enable synchronizeOnSession so that session access is thread-safe.
	// TODO: o.s.validation.Errors / o.s.v.BindingResult for catching errors.

	private final GameStore gameStore;
	private final GameStatusTransformer transformer = new GameStatusTransformer();

	public MainController(GameStore gameStore) {
		this.gameStore = gameStore;
	}

	@GetMapping
	public ModelAndView index(BowlForm bowlForm) {
		Game game = gameStore.getCurrentGame();
		return new ModelAndView("index", "gameStatus", transformer.statusFrom(game));
	}

	@PostMapping
	public ModelAndView recordBowl(BowlForm bowlForm,
											 BindingResult bowlFormBR) {
		Game game = gameStore.getCurrentGame();
		try {
			game.bowl(bowlForm.getPins());
			bowlForm.clear();
		} catch (IllegalBowlException e) {
			bowlFormBR.reject("BOWL-0001",
				format("Sorry, there's no way you knocked down %d pins; there were only %d standing!",
				e.getPinsClaimedToBeKnockedDown(),
				e.getPinsStanding()));
		}
		return new ModelAndView("index", "gameStatus", transformer.statusFrom(game));
	}
}
