package com.facialrecognition.detectfaces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class TrainModel {
	private String path;

	public void train() throws IOException {
		//System.load("C:/Programs/opencv/build/java/x64/opencv_java2413.dll");
		//OpenCVNativeLoader sCvNativeLoader = new OpenCVNativeLoader();
		// sCvNativeLoader.init();

		// Load the classifier
		CascadeClassifier faceDetector = new CascadeClassifier(
				"C:/Users/Zein/Desktop/haarcascade_frontalface_default.xml");

		// check if the cascade
		if (faceDetector.empty()) {
			System.out.println("its empty man");
		}
		// create the model to train

		System.load("C:/Programs/opencv4/opencv/build/java/x64/opencv_java401.dll");
		
		//System.load("C:/Jars/opencv_custom/opencv3333/bin/Release/opencv_face410.pdb");
		//LBPHFaceRecognizer model= LBPHFaceRecognizer.create();

		// A set of data to train the model
		List<Mat> trainingFaces = new ArrayList<>();
		Mat labels = new Mat();

		path = "C:/Users/Zein/Desktop/faces/";
		for (int i = 0; i < 100; i++) {
			path += +i + ".jpg";
			trainingFaces.add(Imgcodecs.imread(path, Imgproc.COLOR_BayerGR2GRAY));
		}
		//model.train(trainingFaces, labels);
		System.out.println("model is trained");

	}
}
