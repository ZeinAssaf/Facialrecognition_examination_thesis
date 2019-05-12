package se.anderssonbilly.faceRecogSettings.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import se.anderssonbilly.faceRecogSettings.model.UserDto;


public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

	@Override
	public void initialize(PasswordMatches constraintAnnotation) {
	}

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		UserDto user = (UserDto) obj;
		return (user.getPassword().length() > 0 && user.getMatchingPassword().length() > 0)
				? user.getPassword().equals(user.getMatchingPassword())
				: false;

	}
}