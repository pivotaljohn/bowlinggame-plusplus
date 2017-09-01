package io.pivotal.la.practice.bowlinggenius;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView recordBowl(@RequestParam("pins") int pins) {
		Map<String, Integer> model = new HashMap<>();
		model.put("score", pins);
		return new ModelAndView("index", model);
	}
}
