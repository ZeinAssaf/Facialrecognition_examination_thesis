package se.anderssonbilly.faceRecogSettings.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import se.anderssonbilly.faceRecogSettings.dao.FaceEntity;
import se.anderssonbilly.faceRecogSettings.dao.FaceRepository;
import se.anderssonbilly.faceRecogSettings.dao.SettingsEntity;
import se.anderssonbilly.faceRecogSettings.dao.SettingsRepository;
import se.anderssonbilly.faceRecogSettings.service.SecurityServiceImpl;
import se.anderssonbilly.faceRecogSettings.service.UserServiceImpl;

@Controller
public class SettingsController {

	@Autowired
	UserServiceImpl userService;
	@Autowired
	SecurityServiceImpl securityService;
	@Autowired
	FaceRepository faceRepository;
	@Autowired
	SettingsRepository settingsRepository;

	@RequestMapping("/settings")
	public String index(Model model) {

		model.addAttribute("settings",
				userService.findByUsername(securityService.findLoggedInUsername()).getSettings());

		return "settings";
	}

	@RequestMapping(value = "/addFace", method = RequestMethod.POST)
	public ResponseEntity<String> addFace(@RequestBody String name) {

		System.out.println("Adding face with name: " + name);

		// TODO validate input
		FaceEntity face = new FaceEntity();
		face.setName(name);
		face.setSettings(userService.findByUsername(securityService.findLoggedInUsername()).getSettings());
		faceRepository.save(face);

		return new ResponseEntity<>("responseText", HttpStatus.OK);
	}

	@RequestMapping(value = "/removeFace", method = RequestMethod.POST)
	public ResponseEntity<String> removeFace(@RequestBody long id) {

		System.out.println("Remove face with id: " + id);

		// TODO validate input
		faceRepository.deleteById(id);

		return new ResponseEntity<>("responseText", HttpStatus.OK);
	}

	@RequestMapping(value = "/getFaces", method = RequestMethod.GET)
	public ResponseEntity<Set<FaceEntity>> getFace() {

		Set<FaceEntity> faces = userService.findByUsername(securityService.findLoggedInUsername()).getSettings()
				.getFaces();

		return new ResponseEntity<>(faces, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateNotifyIf", method = RequestMethod.POST)
	public ResponseEntity<String> updateNotifyIf(@RequestBody boolean notifyIf) {

		System.out.println("Notify if face is detected " + notifyIf);

		SettingsEntity settings = userService.findByUsername(securityService.findLoggedInUsername()).getSettings();
		if (settings.isNotifyIfDetected() != notifyIf) {
			settings.setNotifyIfDetected(notifyIf);
			settingsRepository.save(settings);
			return new ResponseEntity<>("Success", HttpStatus.OK);
		} else
			return new ResponseEntity<>("No change was made", HttpStatus.BAD_REQUEST);

	}
}
