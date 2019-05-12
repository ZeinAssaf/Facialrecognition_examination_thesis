package com.fr.services;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.fr.dao.ImageDoa;
import com.fr.helper.FrHelper;
import com.fr.notifications.SmsNotification;


public class FrService {
	private FrHelper helper= new FrHelper();
	private ImageDoa imageDoa= new ImageDoa();
	private SmsNotification smsNotification= new SmsNotification();


	public boolean handleImage(String code) throws IOException {
		BufferedImage image=helper.decodeImage(code);
		if (imageDoa.recogniseFace(image)==true) {
			return true;
		}
		return false;
		
	}

}
