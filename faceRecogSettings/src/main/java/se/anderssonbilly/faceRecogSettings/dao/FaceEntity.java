package se.anderssonbilly.faceRecogSettings.dao;


import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "face")
public class FaceEntity {

	private Long id;

	@Column(unique = true)
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
