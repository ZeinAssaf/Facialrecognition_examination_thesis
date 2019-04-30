package com.fr.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.DoubleFVComparison;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.model.EigenImages;

public class FrService {
	public BufferedImage recieveFace(String codedFace) throws IOException {
		byte[] codedImage = Base64.getDecoder().decode(codedFace);
		ByteArrayInputStream recievedImage = new ByteArrayInputStream(codedImage);
		BufferedImage image = ImageIO.read(recievedImage);
		return image;
	}

	public String recogniseFace(FImage face) throws IOException {
		List<FImage> basisImages = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			// read from the database
			FImage image = ImageUtilities.readF(new File("C:/Users/Zein/Desktop/myfaces/Actress/" + i + ".jpg"));
			basisImages.add(image);
		}
		// Number of vectors
		int eigenvectors = 100;
		EigenImages eigenModel = new EigenImages(eigenvectors);
		eigenModel.train(basisImages);
		System.out.println("Model is trained");
		DoubleFV[] features = new DoubleFV[2];// number of samples, this will be a fixed number in the database
		for (int i = 0; i < features.length; i++) {
			// Extract the features from the learned images
			FImage image = ImageUtilities.readF(new File("C:/Users/Zein/Desktop/myfaces/Actress/" + i + ".jpg"));
			features[i] = eigenModel.extractFeature(image);
		}
		DoubleFV feature = eigenModel.extractFeature(face);

		for (int i = 0; i < features.length; i++) {

			double distance = features[i].compare(feature, DoubleFVComparison.EUCLIDEAN);
			return String.valueOf(distance);
		}
		return null;

	}

}
