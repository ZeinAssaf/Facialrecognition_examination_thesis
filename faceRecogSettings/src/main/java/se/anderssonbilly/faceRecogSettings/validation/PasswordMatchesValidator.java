package se.anderssonbilly.faceRecogSettings.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import se.anderssonbilly.faceRecogSettings.model.PasswordValidation;


public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

	@Override
	public void initialize(PasswordMatches constraintAnnotation) {
	}

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		PasswordValidation validate = (PasswordValidation) obj;
		return (validate.getPassword().length() > 0 && validate.getMatchingPassword().length() > 0)
				? validate.getPassword().equals(validate.getMatchingPassword())
				: false;

	}
}