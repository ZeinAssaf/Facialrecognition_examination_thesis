package com.fr.dao;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hamcrest.TypeSafeMatcher;
import org.hibernate.Session;
import org.netlib.util.doubleW;
import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.DoubleFVComparison;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.model.EigenImages;

import com.fr.facerecognition.FaceRecognition;
@SuppressWarnings("unchecked")
public class ImageDoa {
	FaceRecognition faceRecognition = new FaceRecognition();
	
	public boolean recogniseFace(BufferedImage capturedFace) throws IOException {
		Session session = CustomSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		List<FImage> basisImages = session.createQuery("from FaceEntity s where s.getSettings='something'")
				.getResultList();
		EigenImages eigenModel = faceRecognition.learnFaces(basisImages);
		
		FImage face=faceRecognition.convertBufferedFImage(capturedFace);
		
		List<Double> distances = new ArrayList<>();
		for (FImage faceInDatabse : basisImages) {
			double distance=faceRecognition.recognize(eigenModel, face, faceInDatabse);
			distances.add(distance);
		}
		if (Collections.max(distances) > 10) {
			return true;
		}
		return false;
		
	}

}
