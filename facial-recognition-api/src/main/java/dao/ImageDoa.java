package dao;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.openimaj.image.FImage;
import org.openimaj.image.model.EigenImages;

import entity.FaceEntity;
import entity.SettingsEntity;
import facerecognition.FaceRecognition;

@SuppressWarnings("unchecked")
public class ImageDoa {
	FaceRecognition faceRecognition = new FaceRecognition();

	public boolean recogniseFace(String api_key, BufferedImage capturedFace) throws IOException {
		Session session = CustomSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		SettingsEntity user = (SettingsEntity) session
				.createQuery("from SettingsEntity e where e.apiKey= :api_key")
				.setParameter("api_key", api_key)
				.getSingleResult();
		List<FaceEntity> faceObjects = session
				.createQuery("from FaceEntity f where f.settings= :setteing_id")
				.setParameter("setteing_id", user)
				.getResultList();
		
		List<FImage> basisImages = new ArrayList<FImage>();
		for (FaceEntity faceEntity : faceObjects) {
			basisImages.add(
					faceRecognition.readImage(
							faceEntity.getSettings().getId().toString(),
							faceEntity.getId().toString()));
		}

		session.getTransaction().commit();

		EigenImages eigenModel = faceRecognition.learnFaces(basisImages);
		FImage face = faceRecognition.convertBufferedFImage(capturedFace);

		List<Double> distances = new ArrayList<>();
		for (FImage faceInDatabse : basisImages) {
			double distance = faceRecognition.recognize(eigenModel, face, faceInDatabse);
			distances.add(distance);
		}

		if (Collections.max(distances) > 10) {
			return true;
		}
		return false;

	}

}
