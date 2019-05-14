package com.fr.dao;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.openimaj.image.FImage;
import org.openimaj.image.model.EigenImages;

import com.fr.entity.SettingsEntity;
import com.fr.facerecognition.FaceRecognition;

@SuppressWarnings("unchecked")
public class ImageDoa {
	FaceRecognition faceRecognition = new FaceRecognition();

	public boolean recogniseFace(String api_key, BufferedImage capturedFace) throws IOException {
		Session session = CustomSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		SettingsEntity user = (SettingsEntity) session
				.createQuery("SELECT s from SettingsEntity s where s.apiKey='123'").getSingleResult();
		List<FImage> basisImages = session.createQuery("SELECT f from FaceEntity f where f.settings=" + user.getId())
				.getResultList();

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
