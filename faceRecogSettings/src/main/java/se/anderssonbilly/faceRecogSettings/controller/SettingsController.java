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
import se.anderssonbilly.faceRecogSettings.dao.NotificationEntity;
import se.anderssonbilly.faceRecogSettings.dao.NotificationRepository;
import se.anderssonbilly.faceRecogSettings.dao.NotificationTypeEnum;
import se.anderssonbilly.faceRecogSettings.dao.SettingsEntity;
import se.anderssonbilly.faceRecogSettings.dao.SettingsRepository;
import se.anderssonbilly.faceRecogSettings.model.FaceDto;
import se.anderssonbilly.faceRecogSettings.model.NotificationDto;
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
	@Autowired
	NotificationRepository notificationRepository;
	
	@RequestMapping("/settings")
	public String index(Model model) {

		model.addAttribute("settings",
				userService.findByUsername(securityService.findLoggedInUsername()).getSettings());

		return "settings";
	}

	@RequestMapping(value = "/addFace", method = RequestMethod.POST)
	public ResponseEntity<String> addFace(@RequestBody FaceDto faceDto) {

		System.out.println("Adding face with name: " + faceDto.getName());
		faceDto.trimName();
		
		if(faceDto.getName() != null && !faceDto.getName().isEmpty()) {
			SettingsEntity settings = userService.findByUsername(securityService.findLoggedInUsername()).getSettings();
			if(faceRepository.findByNameAndSettingsId(faceDto.getName(), settings.getId()) == null) {
				FaceEntity face = new FaceEntity();
				face.setName(faceDto.getName());
				face.setSettings(settings);
				faceRepository.save(face);	
				return new ResponseEntity<>("Success: Name added successfully", HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Error: Name already exsists", HttpStatus.CONFLICT);
			}
		}else {
			return new ResponseEntity<>("Error: Name can't be empty", HttpStatus.LENGTH_REQUIRED);	
		}
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
	
	@RequestMapping(value = "/addNotification", method = RequestMethod.POST)
	public ResponseEntity<String> addNotification(@RequestBody NotificationDto notificationDto) {

		System.out.println("Adding notification with method: " + notificationDto.getType() + " and name: " + notificationDto.getName());
		
		// TODO validate input
		NotificationEntity notification = new NotificationEntity();
		notification.setName(notificationDto.getName());
		notification.setType(NotificationTypeEnum.valueOf(notificationDto.getType()));
		notification.setSettings(userService.findByUsername(securityService.findLoggedInUsername()).getSettings());
		notificationRepository.save(notification);
		
		return new ResponseEntity<>("responseText", HttpStatus.OK);
	}

	@RequestMapping(value = "/removeNotification", method = RequestMethod.POST)
	public ResponseEntity<String> removeNotification(@RequestBody long id) {

		System.out.println("Remove notification with id: " + id);

		// TODO validate input
		notificationRepository.deleteById(id);
		
		return new ResponseEntity<>("responseText", HttpStatus.OK);
	}

	@RequestMapping(value = "/getNotifications", method = RequestMethod.GET)
	public ResponseEntity<Set<NotificationEntity>> getNotifications() {

		Set<NotificationEntity> notifications = userService.findByUsername(securityService.findLoggedInUsername()).getSettings()
				.getNotifications();
		
		return new ResponseEntity<>(notifications, HttpStatus.OK);
	}
}
