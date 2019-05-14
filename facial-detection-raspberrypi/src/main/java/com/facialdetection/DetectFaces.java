package com.facialdetection;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.imageio.ImageIO;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.colour.Transforms;
import org.openimaj.image.processing.face.alignment.AffineAligner;
import org.openimaj.image.processing.face.detection.FaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;
import org.openimaj.video.Video;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.capture.VideoCaptureException;

public class DetectFaces {
	private static Video<MBFImage> video;
	private static VideoDisplay<MBFImage> display;
	private FaceDetector<KEDetectedFace, FImage> faceDetector;
	private List<KEDetectedFace> faces;
	private AffineAligner aligner;
	private FImage faceCapture;

	public void detectFace() throws VideoCaptureException {
		// Create the video object
		video = new VideoCapture(320, 240);
		// Create the object that will display the video and it will capture the video
		// from the first attached camera
		display = VideoDisplay.createVideoDisplay(video);
		// Listener to listen to the video and capture a face from it if it's found
		display.addVideoListener(new VideoDisplayListener<MBFImage>() {
			public void afterUpdate(VideoDisplay<MBFImage> display) {
			}

			// Here the capturing of a face happens
			public void beforeUpdate(MBFImage frame) {
				// A list to store the captured faces
				faceDetector = new FKEFaceDetector(60);
				faces = faceDetector.detectFaces(Transforms.calculateIntensity(frame));// detect a face from each frame
				try {
					// of the video
					aligner = new AffineAligner(); // the aligner aligns the face in a right position to be recognized
													// later
					for (KEDetectedFace face : faces) {
						aligner.align(face);
						faceCapture = face.getFacePatch();
						BufferedImage asd = ImageUtilities.createBufferedImageForDisplay(faceCapture, null);
						asd = resize(asd);
						ImageIO.write(asd, "jpg", new File("C:/Users/Zein/Desktop/new.jpg"));
						frame.drawShape(face.getBounds(), RGBColour.RED);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public byte[] encryptAES(String encodedMessage, SecretKey secretKey, byte[] initializationVector)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		final String AES_CIPHER = "AES/CBC/PKCS5Padding";
		Cipher cipher = Cipher.getInstance(AES_CIPHER);
		IvParameterSpec parameterSpec = new IvParameterSpec(initializationVector);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
		return cipher.doFinal(encodedMessage.getBytes());
	}
	public String convertImageToString(BufferedImage image) {
		ByteArrayOutputStream arrayFace = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", arrayFace);
			byte[] byteFace = arrayFace.toByteArray();
			String encodedImage = Base64.getEncoder().encodeToString(byteFace);
			return encodedImage;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public BufferedImage resize(BufferedImage input) throws IOException {
		BufferedImage output = new BufferedImage(89, 114, input.getType());
		Graphics2D g = output.createGraphics();

		g.drawImage(input, 0, 0, 89, 114, null);
		g.dispose();
		ImageIO.write(output, "jpg", new File("C:/Users/Zein/Desktop/resizedFaces/face.jpg"));
		return output;

	}

	public void postImage(String targetURL, String encodedImage) {
		try {
			Client client = ClientBuilder.newClient();
			Response response = client.target(targetURL).request().post(Entity.json(encodedImage));

			// TODO to be removed later
			System.out.println(response);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
