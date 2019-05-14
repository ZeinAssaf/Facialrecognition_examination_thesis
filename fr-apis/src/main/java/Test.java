import java.util.List;

import org.hibernate.Session;
import org.openimaj.image.FImage;

import com.fr.dao.CustomSessionFactory;
import com.fr.entity.SettingsEntity;

public class Test {
	public static void main(String[] args) {
		Session session = CustomSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		
		SettingsEntity user = (SettingsEntity)session.createQuery("SELECT s from SettingsEntity s where s.apiKey='123'").getSingleResult();
		List<FImage> basisImages = session.createQuery("SELECT f from FaceEntity f where f.settings=" + user.getId())
			.getResultList();
		//SettingsEntity user=session.byNaturalId(SettingsEntity.class).using("apiKey", "123").load();
		System.out.println("hello im alraaiaiaiaishahajlshdajdjahdjklhsal "+user.getApiKey());
		session.getTransaction().commit();
	}

}
