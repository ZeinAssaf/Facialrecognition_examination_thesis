package dao;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.DoubleFVComparison;
import org.openimaj.image.FImage;

import entity.FaceEntity;
import entity.SettingsEntity;
import facerecognition.FaceRecognition;

@SuppressWarnings("unchecked")
public class ImageDoa {
	FaceRecognition faceRecognition = new FaceRecognition();

	public SettingsEntity getUser(String apiKey) {
		Session session = CustomSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		SettingsEntity user = (SettingsEntity) session.createQuery("from SettingsEntity e where e.apiKey= :api_key")
				.setParameter("api_key", apiKey).getSingleResult();
		session.getTransaction().commit();
		return user;
	}
	public static List<FaceEntity> getFaces(SettingsEntity user) {
		Session session = CustomSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		List<FaceEntity> faceObjects = session.createQuery("from FaceEntity f where f.settings= :setteing_id")
				.setParameter("setteing_id", user).getResultList();
		session.getTransaction().commit();
		return faceObjects;
	}
	

	public boolean recogniseFace(SettingsEntity user, double[] capturedFacialKeyPoints) throws IOException {
		List<FaceEntity> faceObjects=getFaces(user);
		List<double[]> faces = new ArrayList<double[]>();
		for (FaceEntity faceEntity : faceObjects) {
			faces.add(faceRecognition.readFile(user, faceEntity));
		}
		DoubleFV capturedFeature = new DoubleFV(capturedFacialKeyPoints);
		for (double[] ds : faces) {
			DoubleFV feature = new DoubleFV(ds);
			double distance = capturedFeature.compare(feature, DoubleFVComparison.EUCLIDEAN);
			if (distance < 0.5) {
				return true;
			}

		}
		return false;

	}

	public void saveImageForRecognition(SettingsEntity user, BufferedImage capturedFace) throws IOException {
		FImage face = faceRecognition.convertBufferedFImage(capturedFace);
		faceRecognition.writeImage(face

				, user.getId().toString(), user.getRecognitonId().toString());
	}

}
