package com.facialdetection;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.openimaj.video.capture.VideoCaptureException;

public class App {
	public static void main(String[] args) {

		DetectFaces detector = new DetectFaces();
		String targeturl = "http://localhost:8080/fr-apis/api/image";

		try {
			detector.detectFace();
			Client client = ClientBuilder.newClient();
			
			//change the path to the image to be sent
			BufferedImage face = ImageIO.read(new File("Path to the image to be sent"));
			// Convert the image to byte array
			ByteArrayOutputStream arrayFace = new ByteArrayOutputStream();
			ImageIO.write(face, "png", arrayFace);
			byte[] byteFace = arrayFace.toByteArray();
			// Encode the byte array to base64
			String encodedImage = Base64.getEncoder().encodeToString(byteFace);
			System.out.println(encodedImage);

			// Send the image as json
			Response base = client.target(targeturl).request().post(Entity.json(encodedImage));
			System.out.println(base);

		} catch (VideoCaptureException e) {
			e.printStackTrace();
			System.err.println("There is no camera attached");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
