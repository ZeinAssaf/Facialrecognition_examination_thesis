package com.fr.notifications;

import com.fr.entity.NotificationEntity;

public class NotificationFactory {
	private Notification notification;

	public Notification getNotification(NotificationEntity entity) {
		switch (entity.getType()) {
		case email:
			return notification = new EmailNotification();
		case sms:
			return notification = new SmsNotification();
		default:
			break;
		}
		return null;
	}

}
