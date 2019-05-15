package notifications;


import entity.NotificationEntity;

public class NotificationFactory {

	public Notification getNotification(NotificationEntity entity) {
		Notification notification;
		switch (entity.getType()) {
		case email:
			return notification = new EmailNotification();

		case sms:
			return notification = new SmsNotification();
		default:
			System.out.println("im null");
			return null;
		}

	}

}
