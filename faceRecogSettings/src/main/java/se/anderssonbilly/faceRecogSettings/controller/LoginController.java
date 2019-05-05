package se.anderssonbilly.faceRecogSettings.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import se.anderssonbilly.faceRecogSettings.service.SecurityServiceImpl;

@Controller
public class LoginController {
	@Autowired
	private SecurityServiceImpl securityService;

	@RequestMapping("/login")
	public String index(Model model) {
		return (securityService.isAuthenticated()) ? "index" : "login";
	}
}
