package dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import entity.FaceEntity;
import entity.NotificationEntity;
import entity.RoleEntity;
import entity.SettingsEntity;
import entity.UserEntity;

public class CustomSessionFactory {
	private static SessionFactory sessionFactory;
	private static StandardServiceRegistry standardServiceRegistry;

	private CustomSessionFactory() {
	}

	public static SessionFactory getInstance() {
		if (sessionFactory == null) {
			standardServiceRegistry = new StandardServiceRegistryBuilder().configure().build();
			MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);
			Metadata metadata = metadataSources
					.addAnnotatedClass(FaceEntity.class)
					.addAnnotatedClass(NotificationEntity.class)
					.addAnnotatedClass(RoleEntity.class)
					.addAnnotatedClass(SettingsEntity.class)
					.addAnnotatedClass(UserEntity.class)
					.getMetadataBuilder()
					.build();
			sessionFactory = metadata.getSessionFactoryBuilder().build();
			return sessionFactory;
		}
		return sessionFactory;
	}
}
