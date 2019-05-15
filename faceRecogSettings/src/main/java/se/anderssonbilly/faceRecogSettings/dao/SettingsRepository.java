package se.anderssonbilly.faceRecogSettings.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepository extends JpaRepository<SettingsEntity, Long> {
	boolean findByApiKey(String apiKey);
}
