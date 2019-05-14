package se.anderssonbilly.faceRecogSettings.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import se.anderssonbilly.faceRecogSettings.model.NotificationDto;

@Service
public class NotificationValidator {

	private final Pattern EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	private final Pattern PHONE_REGEX = Pattern.compile("[0-9]+", Pattern.CASE_INSENSITIVE);
	
	public boolean validate(NotificationDto dto) {
		
		Matcher m;
		
		switch (dto.getType()) {
		case "email":
			m = EMAIL_REGEX.matcher(dto.getName());
			return m.find();
		case "sms":
			m = PHONE_REGEX.matcher(dto.getName());
			return m.find();
		}

		return false;
	}
}
