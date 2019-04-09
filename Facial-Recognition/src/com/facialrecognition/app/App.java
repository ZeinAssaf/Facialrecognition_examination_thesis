
package com.facialrecognition.app;

import java.io.IOException;

import org.opencv.core.Core;
import org.opencv.osgi.OpenCVNativeLoader;

import com.facialrecognition.detectfaces.DetectFace;
import com.facialrecognition.detectfaces.TrainModel;

public class App {
	public static void main(String[] args) throws IOException {
		System.load("C:/Programs/opencv4/opencv/build/java/x64/opencv_java401.dll");
		DetectFace detectFace= new DetectFace();
		detectFace.detect();

		/*
		 * TrainModel trainModel = new TrainModel();
		 * 
		 * trainModel.train();
		 */

	}

}
