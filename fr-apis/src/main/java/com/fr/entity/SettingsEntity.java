package com.fr.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "settings")
public class SettingsEntity {

	private Long id;

	@Column(unique = true)
	private String apiKey;

	private UserEntity user;

	private Long recognitonId = (long) 0;

	private Set<FaceEntity> faces;

	private Set<NotificationEntity> notifications;

	private boolean notifyIfDetected = true;

	@OneToOne(mappedBy = "settings")
	public UserEntity getUser() {
		return user;
	}

	@OneToMany(mappedBy = "settings")
	public Set<FaceEntity> getFaces() {
		return faces;
	}

	@OneToMany(mappedBy = "settings")
	public Set<NotificationEntity> getNotifications() {
		return notifications;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

}