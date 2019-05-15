package notifications;



import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.NoArgsConstructor;
@NoArgsConstructor
public class SmsNotification implements Notification {
	@Override
	public void notifyUser(String recieversPhoneNumber, String textMessage) {
		Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("AUTH_TOKEN"));
		PhoneNumber reciever = new PhoneNumber(recieversPhoneNumber);
		PhoneNumber sender=new PhoneNumber(System.getenv("TWILIO_PN"));
		Message message = Message.creator(reciever, sender, textMessage).create();
		System.out.println(message.getAccountSid());
	}

}
