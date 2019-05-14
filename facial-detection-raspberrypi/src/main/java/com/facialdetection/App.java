package com.facialdetection;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openimaj.video.capture.VideoCaptureException;

public class App {
	public static void main(String[] args) {

		DetectFaces detector = new DetectFaces();
		String targeturl = "http://localhost:8080/fr-apis/api/image";
		

		try {
			detector.detectFace();
			while(true) {
				BufferedImage face = ImageIO.read(new File("C:/Users/Zein/Desktop/new.jpg"));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
