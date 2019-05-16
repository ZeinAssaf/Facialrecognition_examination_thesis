package dao;

import java.util.List;

import org.hibernate.Session;

import entity.NotificationEntity;
import entity.SettingsEntity;
import notifications.Notification;
import notifications.NotificationFactory;

@SuppressWarnings("unchecked")
public class NotificationDao {
	private static final String MESSAGE = "Someone with a wierd face entered the room";
	private NotificationFactory factory = new NotificationFactory();

	public void notifyUser(String apiKey) {
		Session session = CustomSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		SettingsEntity user = (SettingsEntity) session.createQuery("from SettingsEntity e where e.apiKey= :api_key")
				.setParameter("api_key", apiKey).getSingleResult();
		System.out.println("hellooooo " + user.getId());

		List<NotificationEntity> notifications = session
				.createQuery("FROM NotificationEntity s where s.settings= :settings_id")
				.setParameter("settings_id", user).getResultList();
		session.getTransaction().commit();
		System.out.println("hellooooo " + notifications.size());
		for (NotificationEntity entity : notifications) {
			if (entity != null) {
				Notification notification = factory.getNotification(entity);
				notification.notifyUser(entity.getName(), MESSAGE);
			}

		}

	}

}
