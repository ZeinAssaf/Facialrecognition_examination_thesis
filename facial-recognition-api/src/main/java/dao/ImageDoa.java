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
	public SettingsEntity getUser(String apiKey) {
		Session session = CustomSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		SettingsEntity user = (SettingsEntity) session
				.createQuery("from SettingsEntity e where e.apiKey= :api_key")
				.setParameter("api_key", apiKey)
				.getSingleResult();
		session.getTransaction().commit();
		return user;
	}

	public boolean recogniseFace(SettingsEntity user, BufferedImage capturedFace) throws IOException {
		Session session = CustomSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		List<FaceEntity> faceObjects = session
				.createQuery("from FaceEntity f where f.settings= :setteing_id")
				.setParameter("setteing_id", user)
				.getResultList();
		session.getTransaction().commit();
		List<FImage> basisImages = new ArrayList<FImage>();
		for (FaceEntity faceEntity : faceObjects) {
			basisImages.add(
					faceRecognition.readImage(
							faceEntity.getSettings()
									  .getId()
									  .toString(),
							faceEntity.getId()
							  		  .toString()));
		}
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
	public void saveImageForRecognition(SettingsEntity user, BufferedImage capturedFace) throws IOException {
		FImage face = faceRecognition.convertBufferedFImage(capturedFace);
		faceRecognition.writeImage(face
				
				,user.getId().toString()
				,user.getRecognitonId().toString());
	}

}
