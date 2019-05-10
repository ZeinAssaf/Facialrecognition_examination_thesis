package se.anderssonbilly.faceRecogSettings.dao;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "notification")
public class NotificationEntity {

	private Long id;

	@Enumerated(EnumType.STRING)
	private NotificationTypeEnum notificationType;
	private String name;
	
	private SettingsEntity settings;
	
	@ManyToOne
	@JoinColumn(name = "settings_id", nullable = false)
	public SettingsEntity getSettings() {
		return settings;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

}