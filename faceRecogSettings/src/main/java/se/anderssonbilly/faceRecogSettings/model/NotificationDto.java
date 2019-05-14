package se.anderssonbilly.faceRecogSettings.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDto{
	private String type;
	private String name;
	
	public void trimName() {
		this.name = this.name.trim();
	}
}
