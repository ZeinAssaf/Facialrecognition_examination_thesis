package se.anderssonbilly.faceRecogSettings.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
import se.anderssonbilly.faceRecogSettings.dao.UserEntity;
import se.anderssonbilly.faceRecogSettings.model.FaceDto;
import se.anderssonbilly.faceRecogSettings.model.NotificationDto;
import se.anderssonbilly.faceRecogSettings.model.PasswordDto;
import se.anderssonbilly.faceRecogSettings.service.SecurityServiceImpl;
import se.anderssonbilly.faceRecogSettings.service.UserServiceImpl;
import se.anderssonbilly.faceRecogSettings.validation.NotificationValidator;

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

	@Autowired
	NotificationValidator notificationValidator;

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

		if (faceDto.getName() == null || faceDto.getName().isEmpty())
			return new ResponseEntity<>("Error: Name can't be empty", HttpStatus.LENGTH_REQUIRED);

		if (faceDto.getName().length() >= 10)
			return new ResponseEntity<>("Error: Name is to long", HttpStatus.PAYLOAD_TOO_LARGE);

		SettingsEntity settings = userService.findByUsername(securityService.findLoggedInUsername()).getSettings();
		if (faceRepository.findByNameAndSettingsId(faceDto.getName(), settings.getId()) != null)
			return new ResponseEntity<>("Error: Name already exsists", HttpStatus.CONFLICT);

		FaceEntity face = new FaceEntity();
		face.setName(faceDto.getName());
		face.setSettings(settings);
		if (faceRepository.save(face) == null)
			return new ResponseEntity<>("Error: Could not save to database", HttpStatus.EXPECTATION_FAILED);

		return new ResponseEntity<>("Success: Name added successfully", HttpStatus.CREATED);

	}

	@RequestMapping(value = "/removeFace", method = RequestMethod.POST)
	public ResponseEntity<String> removeFace(@RequestBody long id) {

		System.out.println("Remove face with id: " + id);

		if (!faceRepository.existsById(id))
			return new ResponseEntity<>("Error: Face not found", HttpStatus.GONE);

		SettingsEntity settings = userService.findByUsername(securityService.findLoggedInUsername()).getSettings();

		faceRepository.deleteById(id);
		if (settings.getRecognitionId() == id) {
			settings.setRecognitionId((long) 0);
			settingsRepository.save(settings);
		}

		if (faceRepository.existsById(id))
			return new ResponseEntity<>("Error: Delete failed", HttpStatus.UNPROCESSABLE_ENTITY);

		return new ResponseEntity<>("Success: Delete successful", HttpStatus.OK);

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
			return new ResponseEntity<>("Error: No change was made", HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/addNotification", method = RequestMethod.POST)
	public ResponseEntity<String> addNotification(@RequestBody NotificationDto notificationDto) {

		System.out.println("Adding notification with method: " + notificationDto.getType() + " and name: "
				+ notificationDto.getName());

		notificationDto.trimName();

		if (notificationDto.getName() == null || notificationDto.getName().isEmpty())
			return new ResponseEntity<>("Error: Request can't be empty", HttpStatus.LENGTH_REQUIRED);

		if (notificationDto.getName().length() >= 50)
			return new ResponseEntity<>("Error: Request is to long", HttpStatus.PAYLOAD_TOO_LARGE);

		if (!notificationValidator.validate(notificationDto))
			return new ResponseEntity<>("Error: Request is malformed", HttpStatus.BAD_REQUEST);

		SettingsEntity settings = userService.findByUsername(securityService.findLoggedInUsername()).getSettings();
		if (notificationRepository.findByNameAndSettingsId(notificationDto.getName(), settings.getId()) != null)
			return new ResponseEntity<>("Error: Notification already exsists", HttpStatus.CONFLICT);

		NotificationEntity notification = new NotificationEntity();
		notification.setName(notificationDto.getName());
		notification.setType(NotificationTypeEnum.valueOf(notificationDto.getType()));
		notification.setSettings(userService.findByUsername(securityService.findLoggedInUsername()).getSettings());
		if (notificationRepository.save(notification) == null)
			return new ResponseEntity<>("Error: Could not save to database", HttpStatus.EXPECTATION_FAILED);

		return new ResponseEntity<>("Success: Notification added successfully", HttpStatus.CREATED);

	}

	@RequestMapping(value = "/removeNotification", method = RequestMethod.POST)
	public ResponseEntity<String> removeNotification(@RequestBody long id) {

		System.out.println("Remove notification with id: " + id);

		if (!notificationRepository.existsById(id))
			return new ResponseEntity<>("Error: Notification not found", HttpStatus.GONE);

		notificationRepository.deleteById(id);
		if (notificationRepository.existsById(id))
			return new ResponseEntity<>("Error: Delete failed", HttpStatus.UNPROCESSABLE_ENTITY);

		return new ResponseEntity<>("Success: Delete successful", HttpStatus.OK);
	}

	@RequestMapping(value = "/getNotifications", method = RequestMethod.GET)
	public ResponseEntity<Set<NotificationEntity>> getNotifications() {
		Set<NotificationEntity> notifications = userService.findByUsername(securityService.findLoggedInUsername())
				.getSettings().getNotifications();

		return new ResponseEntity<>(notifications, HttpStatus.OK);
	}

	@RequestMapping(value = "/learnFace", method = RequestMethod.POST)
	public ResponseEntity<String> learnFace(@RequestBody long id) {

		System.out.println("Learn face with id: " + id);

		if (!faceRepository.existsById(id))
			return new ResponseEntity<>("Error: Face does not exist", HttpStatus.GONE);

		SettingsEntity settings = userService.findByUsername(securityService.findLoggedInUsername()).getSettings();

		if (settings.getRecognitionId() > 0) {
			settings.setRecognitionId((long) 0);
			settingsRepository.save(settings);
			return new ResponseEntity<>("Success: Stopped learning face", HttpStatus.OK);
		}

		settings.setRecognitionId(id);
		settingsRepository.save(settings);

		return new ResponseEntity<>("Success: Started learning face", HttpStatus.OK);
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ResponseEntity<String> changePassword(@RequestBody @Valid PasswordDto passwordDto, BindingResult result) {

		System.out.println("Changing password");

		if (result.hasGlobalErrors())
			if (result.getGlobalError().getCode().equals("PasswordMatches"))
				result.addError(new FieldError("matchingPassword", "matchingPassword",
						result.getGlobalError().getDefaultMessage()));

		String error = "";
		for (FieldError e : result.getFieldErrors()) 
			if (!e.getDefaultMessage().isEmpty())
				error+= e.getDefaultMessage() + ",";
		
		error = error.substring(0, error.length()-1);
		
		if (result.hasErrors())
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

		UserEntity user = userService.findByUsername(securityService.findLoggedInUsername());
		user.setPassword(passwordDto.getPassword());
		userService.save(user);

		return new ResponseEntity<>("Success: Password updated", HttpStatus.OK);
	}
}
