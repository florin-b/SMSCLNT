package model;

import java.rmi.RemoteException;
import java.util.Calendar;

import SMSService.*;
import beans.BeanClient;
import utils.MailOperations;

public class SendSmsClient {

	public static boolean sendSms(String nrTelefon, String codClient, BeanClient client) {

		boolean messageSent = true;

		SMSServicePortProxy proxy = new SMSServicePortProxy();

		try {
			String sessionId = proxy.openSession("arabesque2", "arbsq123");

			String msgText = "Comanda dumneavoastra va fi livrata in urmatoarele 2 ore.";

			proxy.sendSession(sessionId, nrTelefon, msgText, Calendar.getInstance(), "", 0);

			proxy.closeSession(sessionId);

		} catch (RemoteException e) {
			messageSent = false;
		}

		return messageSent;
	}

	public static void main(String[] args) {

		SMSServicePortProxy proxy = new SMSServicePortProxy();

		try {
			String sessionId = proxy.openSession("arabesque2", "arbsq123");

			proxy.sendSession(sessionId, "0742290177", "test", Calendar.getInstance(), "", 0);

			proxy.closeSession(sessionId);

		} catch (RemoteException e) {
			System.out.println("Eroare: " + e.toString());
		}

	}

}
