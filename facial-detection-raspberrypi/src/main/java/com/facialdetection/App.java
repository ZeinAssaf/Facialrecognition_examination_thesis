package com.facialdetection;

import org.openimaj.video.capture.VideoCaptureException;

public class App {
	public static void main(String[] args) {
		DetectFaces detector= new DetectFaces();
		try {
			detector.detectFace();
		} catch (VideoCaptureException e) {
			e.printStackTrace();
			System.err.println("There is no camera attached");
		}
	}

}
