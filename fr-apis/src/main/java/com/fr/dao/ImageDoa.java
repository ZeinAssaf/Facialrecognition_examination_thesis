package com.fr.dao;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.DoubleFVComparison;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.model.EigenImages;

public class ImageDoa {
	public boolean recogniseFace(BufferedImage face) throws IOException {
		List<FImage> basisImages = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			// read from the database
			FImage image = ImageUtilities.readF(new File("C:/Users/Zein/Desktop/db_people/zein/" + i + ".png"));
			basisImages.add(image);
		}
		// Number of vectors
		int eigenvectors = 100;
		EigenImages eigenModel = new EigenImages(eigenvectors);
		eigenModel.train(basisImages);
		System.out.println("Model is trained");
		DoubleFV[] features = new DoubleFV[100];// number of samples, this will be a fixed number in the database
		for (int i = 0; i < features.length; i++) {
			// Extract the features from the learned images
			FImage image = ImageUtilities.readF(new File("C:/Users/Zein/Desktop/db_people/zein/" + i + ".png"));
			features[i] = eigenModel.extractFeature(image);
		}
		FImage image = new FImage(89,114);
		image = ImageUtilities.assignBufferedImage(face, image);
		DoubleFV feature = eigenModel.extractFeature(image);
		List<Double> distances=new ArrayList<>();
		for (int i = 0; i < features.length; i++) {
			double distance = feature.compare(features[i], DoubleFVComparison.EUCLIDEAN);
			distances.add(distance);
		}
		if (Collections.min(distances)>15) {
			return true;
		}
		return false;
	}

}
