package notifications;

import java.io.IOException;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmailNotification implements Notification{
	@Override
	public void notifyUser(String emailReciever,String textMessage) {

	    Email from = new Email("test@example.com");
	    String subject = "Warning";
	    Email to = new Email(emailReciever);
	    Content content = new Content("text/plain", textMessage);
	    Mail mail = new Mail(from, subject, to, content);

	    SendGrid sg = new SendGrid(System.getenv("SENDGRID_API"));
	    Request request = new Request();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(mail.build());
	      Response response = sg.api(request);
	      System.out.println(response.getStatusCode());
	      System.out.println(response.getBody());
	      System.out.println(response.getHeaders());
	    } catch (IOException ex) {
	      ex.printStackTrace();
	    }
	  
	}

}
