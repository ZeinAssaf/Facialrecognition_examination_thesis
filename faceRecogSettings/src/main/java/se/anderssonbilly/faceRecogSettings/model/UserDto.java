package se.anderssonbilly.faceRecogSettings.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import se.anderssonbilly.faceRecogSettings.validation.PasswordMatches;

@Getter
@Setter
@PasswordMatches
public class UserDto extends PasswordValidation{

	@Size(min = 4, max = 12, message = "Username size must be 4-12")
	@NotNull(message = "")
	@Pattern(regexp = "^[A-Za-z0-9]*$", message = "Username contains invalid character")
	@NotBlank(message = "")
	@NotEmpty(message = "Username must not be empty")
	private String username;


}
