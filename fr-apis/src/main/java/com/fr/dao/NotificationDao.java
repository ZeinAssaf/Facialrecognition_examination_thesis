package com.fr.dao;

import java.util.List;
import org.hibernate.Session;

import com.fr.entity.NotificationEntity;
import com.fr.entity.SettingsEntity;
import com.fr.notifications.Notification;
import com.fr.notifications.NotificationFactory;

@SuppressWarnings("unchecked")
public class NotificationDao {
	private Notification notification;
	private static final String MESSAGE = "Someone with a wierd face entered the room";
	private NotificationFactory factory;

	public void notifyUser(String api_key) {
		Session session = CustomSessionFactory
				.getInstance()
				.getCurrentSession();
		SettingsEntity user = (SettingsEntity) session
				.createQuery("SELECT s from SettingsEntity s where s.apiKey=" + api_key)
				.getSingleResult();
		session.beginTransaction();
		List<NotificationEntity> notifications = session
				.createQuery("SELECT s FROM NotificationEntity s where s.settings=" + user.getId())
				.getResultList();
		session.getTransaction().commit();
		for (NotificationEntity n : notifications) {
			notification = factory.getNotification(n);
			notification.notifyUser(n.getName(), MESSAGE);
		}

	}

}
