package se.anderssonbilly.faceRecogSettings.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SettingsController {
	@RequestMapping("/settings")
	public String index(Model model) {
		System.out.println("settings");
		return "settings";
	}
}
