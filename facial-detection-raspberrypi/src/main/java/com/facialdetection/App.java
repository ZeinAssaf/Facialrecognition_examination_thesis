package com.facialdetection;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openimaj.video.capture.VideoCaptureException;


public class App {
	public static void main(String[] args) {

		DetectFaces detector = new DetectFaces();
		String targeturl = "http://localhost:8080/facial-recognition-api/api/face/";

		try {
			detector.detectFace();
			while (true) {
				File image=new File("C:/Users/Zein/Desktop/new.png");
				BufferedImage face = ImageIO.read(image);
				String encodedImage = detector.convertImageToString(face);
				detector.postImage(targeturl, encodedImage);
	
				Thread.sleep(5000);
			}
		} catch (VideoCaptureException e) {
			e.printStackTrace();
			System.err.println("There is no camera attached");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
