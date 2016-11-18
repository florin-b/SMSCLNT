package utils;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class MailOperations {

	public static void sendMail(String mailMessage) {

		String to = "florin.brasoveanu@arabesque.ro";
		String from = "SMS_Clienti";
		String host = "mail.arabesque.ro";

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);

		Session session = Session.getDefaultInstance(properties);

		try {
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			message.setSubject("Distributie");

			message.setText(mailMessage);

			Transport.send(message);

		} catch (MessagingException mex) {
			System.out.println(mex.toString());
		}
	}

}
