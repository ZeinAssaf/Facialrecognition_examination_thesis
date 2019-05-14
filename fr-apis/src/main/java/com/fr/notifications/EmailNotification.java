package com.fr.notifications;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;

public class EmailNotification implements Notification{
	@Override
	public void notifyUser(String emailReciever,String textMessage) {
		MailjetClient client = new MailjetClient(System.getenv("MAILJET_API_KEY_PUBLIC"), System.getenv("MAILJET_API_KEY_PRIVATE"),
				new ClientOptions("v3.1"));
		MailjetRequest request = new MailjetRequest(Emailv31.resource)
				.property(Emailv31.MESSAGES,
						new JSONArray().put(new JSONObject()
								.put(Emailv31.Message.FROM,
										new JSONObject().put("Email", "for.work1@outlook.com").put("Name",
												"Zein and Billy"))
								.put(Emailv31.Message.TO,
										new JSONArray().put(new JSONObject().put("Email", emailReciever).put("Name",
												emailReciever)))
								.put(Emailv31.Message.SUBJECT, "Unautherized access detected")
								.put(Emailv31.Message.TEXTPART, "warning").put(Emailv31.Message.HTMLPART,
										textMessage)));
		try {
			MailjetResponse response = client.post(request);
			System.out.println(response.getStatus());
		} catch (MailjetException e) {
			e.printStackTrace();
		} catch (MailjetSocketTimeoutException e) {
			
			e.printStackTrace();
		}
	}

}
