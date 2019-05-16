package services;

import java.awt.image.BufferedImage;
import java.io.IOException;

import dao.ImageDoa;
import dao.NotificationDao;
import entity.SettingsEntity;
import helper.FrHelper;

public class FrService {
	private FrHelper helper = new FrHelper();
	private ImageDoa imageDoa = new ImageDoa();
	private NotificationDao notificationDao = new NotificationDao();

	public void handleImage(String apiKey, String code) throws IOException {
		BufferedImage image = helper.decodeImage(code);
		SettingsEntity user = imageDoa.getUser(apiKey);
		if (user.getRecognitonId() == 0 && user.isNotifyIfDetected() && imageDoa.recogniseFace(user, image)) {
			notificationDao.notifyUser(apiKey);
		} else if (user.getRecognitonId() == 0 && !user.isNotifyIfDetected() && imageDoa.recogniseFace(user, image)) {
			notificationDao.notifyUser(apiKey);
		} else {
			imageDoa.saveImageForRecognition(user, image);
		}

	}

}
