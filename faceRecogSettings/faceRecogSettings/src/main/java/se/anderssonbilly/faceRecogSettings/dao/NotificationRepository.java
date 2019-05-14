package se.anderssonbilly.faceRecogSettings.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository  extends JpaRepository<NotificationEntity, Long>{
	FaceEntity findByNameAndSettingsId(String name, Long id);
	boolean existsById(Long id);
}
