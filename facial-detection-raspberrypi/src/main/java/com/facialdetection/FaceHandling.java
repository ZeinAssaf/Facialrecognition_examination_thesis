package com.facialdetection;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class FaceHandling {
    // Load the classifier
    private CascadeClassifier faceDetector = new CascadeClassifier("NewFile.xml");
    // create a video capture image
    private VideoCapture videoCapture = new VideoCapture(0);
    private Mat image;
    private MatOfRect detectedFaces;
    private List<String> codedFaces;

    public FaceHandling() {
	// check if the cascade is imported correctly
	if (faceDetector.empty())
	    System.out.println("The cascade is not imported correctly");
    }

    public List<String> detectFace() {
	// create the mat "the face image" to be taken
	image = new Mat();
	// This instance will be filled with detected faces from the taken image
	detectedFaces = new MatOfRect();
	// Capture a snapshot from the camera
	codedFaces = new ArrayList<String>();
	// ReadImage from the camera
	videoCapture.read(image);
	// TODO remove later
	Imgcodecs.imwrite("C:/Users/Zein/Desktop/mytest.jpg", image);
	if (videoCapture.isOpened()) {
	    faceDetector.detectMultiScale(image, detectedFaces);
	    for (Rect face : detectedFaces.toArray()) {
		Mat capturedFace = new Mat(image, face);
		Size size = new Size(650, 650);
		Imgproc.resize(capturedFace, capturedFace, size);
		String base64 = convertMatTobase64(capturedFace);
		codedFaces.add(base64);
		Imgcodecs.imwrite("C:/Users/Zein/Desktop/mytests.jpg", capturedFace);
	    }
	} else {
	    System.out.println("camera is closed");
	}
	return codedFaces;
    }

    public String convertMatTobase64(Mat img) {
	MatOfByte matOfByte = new MatOfByte();
	Imgcodecs.imencode(".jpg", img, matOfByte);
	byte[] faceByte = matOfByte.toArray();
	return Base64.getEncoder().encodeToString(faceByte);
    }

    public Response postImage(String targetURL, String encodedImage, String apiKey) {
	try {
	    Client client = ClientBuilder.newClient();
	    WebTarget target = client.target(targetURL).path("{API_KEY}");
	    Response response = target.resolveTemplate("API_KEY", apiKey).request().post(Entity.json(encodedImage));
	    return response;
	} catch (Exception e) {
	    // TODO: handle exception
	}
	return null;
    }

    public BufferedImage resize(BufferedImage input) throws IOException {
	BufferedImage output = new BufferedImage(640, 640, input.getType());
	Graphics2D g = output.createGraphics();

	g.drawImage(input, 0, 0, 640, 640, null);
	g.dispose();
	return output;
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
	    System.out.println(encodedImage);
	    return encodedImage;
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return null;
    }

}
