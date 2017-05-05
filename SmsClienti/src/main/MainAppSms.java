package main;

import java.util.List;
import java.util.Set;

import beans.BeanClient;
import beans.Borderou;
import beans.StareMasina;
import beans.VitezaMedie;
import database.OperatiiBorderou;
import database.OperatiiMasina;
import database.OperatiiSms;
import database.OperatiiSoferi;
import logs.LogMessage;
import model.VitezeService;
import utils.Constants;
import utils.MailOperations;

public class MainAppSms {

	public static void main(String[] args) {

		try {
			// new OperatiiBorderou().saveCoordonateAdrese(listAdrese);
		} catch (Exception e) {
			e.printStackTrace();
		}

		new MainAppSms().startApp();

	}

	private void startApp() {

		Borderou borderouActiv = null;

		List<VitezaMedie> listViteze = new VitezeService().getVitezeMedii();

		try {
			List<String> listSoferi = new OperatiiSoferi().getListSoferi("GL10");

			for (String sofer : listSoferi) {

				borderouActiv = new OperatiiBorderou().getBorderouActiv(sofer);

				if (borderouActiv.getNrBorderou() != null) {

					StareMasina stareMasina = new OperatiiMasina().getStareMasina(borderouActiv.getNrBorderou());

					if (stareMasina != null && stareMasina.getKilometraj() > 0) {

						Set<BeanClient> listClienti = new OperatiiBorderou().getClientiBorderouFromDB(borderouActiv.getNrBorderou());

						if (listClienti.size() == 0)
							listClienti = new OperatiiBorderou().getClientiBorderouOnline(borderouActiv.getNrBorderou(), stareMasina);

						if (listClienti.size() > 0) {
							new OperatiiSms().expediazaSms(sofer, borderouActiv, listClienti, stareMasina, listViteze);
						}
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			LogMessage.writeErrorLog(e.toString());
			MailOperations.sendMail(e.toString() + " borderou = " + borderouActiv);
		}

	}

}
