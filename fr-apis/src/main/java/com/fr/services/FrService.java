package com.fr.services;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.fr.dao.ImageDoa;
import com.fr.dao.NotificationDao;
import com.fr.helper.FrHelper;
import com.fr.notifications.Notification;
import com.fr.notifications.NotificationFactory;
import com.fr.notifications.SmsNotification;

import eu.larkc.csparql.parser.CSparqlParser.newVarFromExpression_return;


public class FrService {
	private FrHelper helper= new FrHelper();
	private ImageDoa imageDoa= new ImageDoa();
	private NotificationDao notificationDao= new NotificationDao();


	public void handleImage(String api_key, String code) throws IOException {
		BufferedImage image=helper.decodeImage(code);
		if (!imageDoa.recogniseFace(api_key,image)) {
			notificationDao.notifyUser(api_key);
		}
		
	}

}
