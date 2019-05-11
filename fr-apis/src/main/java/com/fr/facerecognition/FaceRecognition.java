package com.fr.facerecognition;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.DoubleFVComparison;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.model.EigenImages;

public class FaceRecognition {

	public EigenImages learnFaces(List<FImage> basisFaces) {
		basisFaces = new ArrayList<>();
		int eigenvectors = 100;
		EigenImages eigenModel = new EigenImages(eigenvectors);
		eigenModel.train(basisFaces);
		return eigenModel;
	}

	public FImage convertBufferedFImage(BufferedImage image) {
		FImage fImage = new FImage(89, 114);
		fImage = ImageUtilities.assignBufferedImage(image, fImage);
		return fImage;
	}

	public double recognize(EigenImages eigenmodel, FImage face1, FImage face2) {
		DoubleFV face1Feature = eigenmodel.extractFeature(face1);
		DoubleFV face2Feature = eigenmodel.extractFeature(face2);
		return face1Feature.compare(face2Feature, DoubleFVComparison.EUCLIDEAN);
	}

}
