package com.facialrecognition.detectfaces;

import java.io.IOException;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class DetectFace {
	public void detect() throws IOException {
		System.out.println("im called");
		// Load the classifier
		CascadeClassifier faceDetector = new CascadeClassifier("C:/your/path/to/haarcascade_frontalface_default.xml");

		// check if the cascade is imported correctly
		if (faceDetector.empty())
			System.out.println("its empty man");

		// create a video capture image
		VideoCapture videoCapture = new VideoCapture(0);

		// create the mat "the face image" to be taken
		Mat image = new Mat();

		// This instance will be filled with detected faces from the taken image
		MatOfRect detectedFaces = new MatOfRect();

		// Capture a snapshot from the camera
		videoCapture.read(image);

		// this image just to make sure that the picture is captured
		Imgcodecs.imwrite("C:/your/path/to/save/the/captured/face/test.jpg", image);
		Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2GRAY);
		if (videoCapture.isOpened()) {
			faceDetector.detectMultiScale(image, detectedFaces);
			for (Rect face : detectedFaces.toArray()) {
				Mat capturedFace = new Mat(image, face);
				Imgcodecs.imwrite("C:/your/path/to/save/the/captured/face/test.jpg", capturedFace);
			}
		} else {
			System.out.println("camera is closed");
		}
	}
}
