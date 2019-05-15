package services;

import java.awt.image.BufferedImage;
import java.io.IOException;

import dao.ImageDoa;
import dao.NotificationDao;
import helper.FrHelper;




public class FrService {
	private FrHelper helper= new FrHelper();
	private ImageDoa imageDoa= new ImageDoa();
	private NotificationDao notificationDao= new NotificationDao();


	public void handleImage(String api_key, String code) throws IOException {
		BufferedImage image=helper.decodeImage(code);
		if (imageDoa.recogniseFace(api_key,image)==false) {
			notificationDao.notifyUser(api_key);
		}
		
	}

}
