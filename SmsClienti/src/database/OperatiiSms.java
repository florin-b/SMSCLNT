package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

import beans.BeanClient;
import beans.Borderou;
import beans.StareMasina;
import model.SendSmsClient;
import queries.SqlQueries;
import utils.Constants;
import utils.DBUtils;

public class OperatiiSms {

	private final double INTERVAL_ALERTA_MIN_H = 0.2;
	private final double INTERVAL_ALERTA_MAX_H = 2;

	public void expediazaSms(String codSofer, Borderou borderou, Set<BeanClient> listClienti, StareMasina stareMasina) throws SQLException {

		double timpSosireH = 0;

		int nrOpriri = OperatiiBorderou.getNrOpriri(borderou.getNrBorderou());
		double distantaTotal = 0;
		double distParcursa = 0;

		for (BeanClient client : listClienti) {

			// init
			if (distantaTotal == 0 && distParcursa == 0) {
				distParcursa = stareMasina.getKilometraj() - client.getInitKm();
			}

			distantaTotal += client.getDistClPrecedent() - distParcursa;

			if (distantaTotal > 0 && distParcursa != 0)
				distParcursa = 0;
			else if (distantaTotal < 0) {
				distParcursa = Math.abs(distantaTotal);
				distantaTotal = 0;

			}

			if (distantaTotal > 0) {

				timpSosireH = (double) (distantaTotal) / Constants.getVitezaMedie_KM_H(borderou.getTipMasina());

				timpSosireH += nrOpriri * Constants.getTimpStationareH(borderou.getTipMasina());

				System.out.println("Timp sosire =" + timpSosireH + " , distanta = " + client);

				if (timpSosireH >= INTERVAL_ALERTA_MIN_H && timpSosireH <= INTERVAL_ALERTA_MAX_H && !client.isSmsEmis()) {
					sendSmsAlert(codSofer, borderou.getNrBorderou(), client, stareMasina);
					System.out.println(
							"Expediere sms =" + borderou.getNrBorderou() + " , client = " + client.getCodClient() + " , adresa = " + client.getCodAdresa());

					nrOpriri++;
				}
			}

		}

	}

	private boolean sendSmsAlert(String codSofer, String borderouActiv, BeanClient client, StareMasina stareMasina) throws SQLException {
		DBManager dbManager = new DBManager();

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		DateFormat timeFormat = new SimpleDateFormat("HHmmss");

		Calendar cal = Calendar.getInstance();

		boolean result = false;

		try (Connection conn = dbManager.getProdConnection(); PreparedStatement stmt = conn.prepareStatement(SqlQueries.sendAlertClient())) {

			stmt.setString(1, codSofer);
			stmt.setString(2, borderouActiv);
			stmt.setString(3, client.getCodClient());
			stmt.setString(4, dateFormat.format(cal.getTime()));
			stmt.setString(5, timeFormat.format(cal.getTime()));
			stmt.setString(6, client.getCodAdresa());
			stmt.setString(7, stareMasina.getCoordonateGps().getLatitude() + "," + stareMasina.getCoordonateGps().getLongitude());
			stmt.setString(8, String.valueOf(stareMasina.getKilometraj()));

			result = stmt.execute();

			String nrTel = getTelNumber(conn, client.getCodClient());

			if (nrTel.trim().length() > 0)
				SendSmsClient.sendSms(nrTel, borderouActiv, client);

		} catch (SQLException ex) {
			System.out.println("Eroare: " + codSofer + " , " + client);
		}

		return result;

	}

	private String getTelNumber(Connection connection, String codClient) throws SQLException {

		String nrTel = "";
		PreparedStatement stmt = connection.prepareStatement(SqlQueries.getTelefonSmsClient());

		stmt.setString(1, codClient);

		stmt.executeQuery();
		ResultSet rs = stmt.getResultSet();

		while (rs.next()) {
			nrTel = rs.getString("tel_number");
		}

		DBUtils.closeConnections(stmt, rs);

		return nrTel;

	}

}
