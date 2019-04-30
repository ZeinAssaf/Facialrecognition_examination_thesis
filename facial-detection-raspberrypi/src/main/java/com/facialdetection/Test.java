package com.facialdetection;

import org.openimaj.video.capture.VideoCaptureException;

public class Test {
	public static void main(String[] args) throws VideoCaptureException {
		DetectFaces detectFaces= new DetectFaces();
		detectFaces.detectFace();
	}

}
