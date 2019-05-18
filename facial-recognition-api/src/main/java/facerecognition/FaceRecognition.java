package facerecognition;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.openimaj.feature.DoubleFV;
import org.openimaj.feature.DoubleFVComparison;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;

import com.google.gson.Gson;

import entity.FaceEntity;
import entity.SettingsEntity;
import pojo.FaceVector;

public class FaceRecognition {

	public FImage convertBufferedFImage(BufferedImage image) {
		FImage fImage = new FImage(89, 114);
		fImage = ImageUtilities.assignBufferedImage(image, fImage);
		return fImage;
	}

	public double recognize(double[] capturedFace, double[] faceIndatabase) {
		DoubleFV featureCapturedFace = new DoubleFV(capturedFace);
		DoubleFV featureFaceInDatabase = new DoubleFV(faceIndatabase);
		return featureCapturedFace.compare(featureFaceInDatabase, DoubleFVComparison.EUCLIDEAN);
	}

//TODO TO BE REMOVED LATER
	public FImage readImage(String folderName, String fileName) throws IOException {
		String path = "C:/Users/Zein/Desktop/faces/" + folderName + "/" + fileName + ".jpg";
		FImage face = ImageUtilities.readF(new File(path));
		return face;
	}

	public double[] readFile(SettingsEntity user, FaceEntity face) throws IOException {
		String path = "C:/Users/Zein/Desktop/faces/" + user.getId() + "/" + face.getId() + ".txt";
		;
		FileReader fileReader = new FileReader(path);
		int x;
		StringBuilder text = new StringBuilder();
		while ((x = fileReader.read()) != -1) {
			text.append((char) x);
		}
		fileReader.close();
		String[] dString = text.toString().split(",");
		double[] facialKeyPoints = new double[dString.length];
		for (int i = 0; i < facialKeyPoints.length; i++) {
			facialKeyPoints[i] = Double.parseDouble(dString[i]);
		}
		return facialKeyPoints;
	}

	// TODO TO BE REMOVED LATER
	public void writeImage(FImage newFace, String folderName, String fileName) throws IOException {
		String path = "C:/Desktop/faces/" + folderName + "/" + fileName;
		ImageUtilities.write(newFace, new File(path));
	}

	public double[] getFaceVector(Response response) {
		String responseBody = getJsonResponseBody(response);
		Gson gson = new Gson();
		FaceVector faceVector = gson.fromJson(responseBody, FaceVector.class);
		return (double[]) faceVector.getVector().get__ndarray__();
	}

	public Response requestFaceVector(String encodedImage) {
		final String headerParamUserID = "user_id";
		final String headerParamUserkey = "user_key";
		final String targetUrl = "http://www.facexapi.com/get_face_vec?face_det=0&bboxes=130,180,240,300";
		Form img = new Form();
		img.param("img", encodedImage);
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.add(headerParamUserID, System.getenv("FACEX_USER_ID"));
		headers.add(headerParamUserkey, System.getenv("FACEX_USER_KEY"));
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(targetUrl);
		return target.request().headers(headers).post(Entity.form(img));

	}

	public String getJsonResponseBody(Response response) {
		String responseBody = response.readEntity(String.class);
		return responseBody;

	}

}
