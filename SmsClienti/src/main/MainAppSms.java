package main;

import java.util.List;
import java.util.Set;

import beans.BeanClient;
import beans.BeanCoordonateClient;
import beans.BeanDistantaClient;
import beans.CoordonateGps;
import database.OperatiiBorderou;
import database.OperatiiMasina;
import database.OperatiiSms;
import database.OperatiiSoferi;
import logs.LogMessage;

public class MainAppSms {

	public static void main(String[] args) {

		new MainAppSms().startApp();

	}

	private void startApp() {
		try {
			List<String> listSoferi = new OperatiiSoferi().getListSoferi("GL10");

			String borderouActiv = "";

			for (String sofer : listSoferi) {

				borderouActiv = new OperatiiBorderou().getBorderouActiv(sofer);

				LogMessage.writeDebugLog("Sofer :" + sofer + " , borderou = " + borderouActiv);

				if (borderouActiv != null) {

					CoordonateGps pozitieMasina = new OperatiiMasina().getPozitieMasina(borderouActiv);

					Set<BeanClient> listClienti = new OperatiiBorderou().getClientiBorderou(borderouActiv);

					List<BeanCoordonateClient> listCoordClienti = new OperatiiBorderou().getCoordonateClienti(listClienti);

					List<BeanDistantaClient> listDistClienti = new OperatiiBorderou().getDistantaClientReal(pozitieMasina, listCoordClienti);

					new OperatiiSms().expediazaSms(sofer, borderouActiv, listClienti, listDistClienti);

				}

			}

		} catch (Exception e) {

			LogMessage.writeErrorLog(e.toString());
		}
	}

}
