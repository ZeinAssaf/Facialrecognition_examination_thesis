package services;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.ws.rs.core.Response;

import dao.ImageDoa;
import dao.NotificationDao;
import entity.SettingsEntity;
import facerecognition.FaceRecognition;
import helper.FrHelper;

public class FrService {
	private FrHelper helper = new FrHelper();
	private ImageDoa imageDoa = new ImageDoa();
	private NotificationDao notificationDao = new NotificationDao();
	private FaceRecognition faceRecognition=new FaceRecognition();

	public void handleImage(String apiKey, String code) throws IOException {
		Response response = faceRecognition.requestFaceVector(code);
		double[] facialKeyPoints = faceRecognition.getFaceVector(response);
		BufferedImage image = helper.decodeImage(code);
		SettingsEntity user = imageDoa.getUser(apiKey);
		if (user.getRecognitonId() == 0 && user.isNotifyIfDetected() && imageDoa.recogniseFace(user, facialKeyPoints)) {
			notificationDao.notifyUser(apiKey);
		} else if (user.getRecognitonId() == 0 && !user.isNotifyIfDetected() && !imageDoa.recogniseFace(user, facialKeyPoints)) {
			notificationDao.notifyUser(apiKey);
		} else {
			imageDoa.saveImageForRecognition(user, image);
		}

	}

}
