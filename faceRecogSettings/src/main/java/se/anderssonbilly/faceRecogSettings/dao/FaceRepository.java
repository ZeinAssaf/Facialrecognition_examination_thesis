package se.anderssonbilly.faceRecogSettings.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaceRepository  extends JpaRepository<FaceEntity, Long>{
	List<FaceEntity> findBySettingsId(Long id);
	FaceEntity findByNameAndSettingsId(String name, Long id);
	boolean existsById(Long id);
}
