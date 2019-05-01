package com.fr.services;

import java.awt.Graphics2D;
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

	/**
	 * This method is used to make all the captured images in the same size so they
	 * can be compared
	 *
	 * @param path   is the path to the image
	 * @param name   file name
	 * @param width
	 * @param height
	 */
	public void resizeImage(String path, String name, int width, int height, String filetype) {
		try {
			BufferedImage input = ImageIO.read(new File(path));
			BufferedImage output = new BufferedImage(width, height, input.getType());
			Graphics2D g = output.createGraphics();
			g.drawImage(input, 0, 0, width, height, null);
			g.dispose();
			ImageIO.write(output, filetype, new File(path + name));

		} catch (Exception e) {
			System.err.println("File not found or currupted, check that teh specified file is an image");
			e.printStackTrace();
		}

	}

}
