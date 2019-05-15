package test;



import java.io.IOException;

import com.android.dx.dex.code.ZeroSizeInsn;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import dao.NotificationDao;


public class MyTest {
	public static void main(String[] args) {

        NotificationDao notificationDao=new NotificationDao();
        notificationDao.notifyUser("123");
        /*
        Email from = new Email("test@example.com");
	    String subject = "Warning";
	    Email to = new Email("zein.m.assaf@gmail.com");
	    Content content = new Content("text/plain", "helloooooooooooooo");
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
	    }*/
	  
		
	}

}
