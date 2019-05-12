package com.fr.notifications;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsNotification {

	public void notifyUser(String recieversPhoneNumber, String textMessage) {
		Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("AUTH_TOKEN"));
		PhoneNumber reciever = new PhoneNumber(recieversPhoneNumber);
		Message message = Message.creator(reciever, System.getenv("TWILIO_PN"), textMessage).create();
		System.out.println(message.getAccountSid());
	}

}
