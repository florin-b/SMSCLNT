package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import beans.BeanClient;
import beans.BeanDistantaClient;
import queries.SqlQueries;

public class OperatiiSms {

	private final int VITEZA_MEDIE_KM_H = 45;

	private final double INTERVAL_ALERTA_MIN_H = 0.5;
	private final double INTERVAL_ALERTA_MAX_H = 2;

	public void expediazaSms(String codSofer, String borderouActiv, Set<BeanClient> listClienti, List<BeanDistantaClient> listDistClienti) throws SQLException {

		double timpSosire = 0;

		for (BeanDistantaClient distanta : listDistClienti) {
			timpSosire = (distanta.getDistanta() / 1000) / VITEZA_MEDIE_KM_H;

			if (timpSosire >= INTERVAL_ALERTA_MIN_H && timpSosire <= INTERVAL_ALERTA_MAX_H) {
				checkStareAlert(codSofer, borderouActiv, distanta.codClient);
			}

		}

	}

	private void checkStareAlert(String codSofer, String borderouActiv, String codClient) throws SQLException {
		if (!isAlertSent(codSofer, borderouActiv, codClient))
			sendSmsAlert(codSofer, borderouActiv, codClient);
	}

	private boolean isAlertSent(String codSofer, String borderouActiv, String codClient) throws SQLException {
		DBManager dbManager = new DBManager();

		boolean isSent = false;

		try (Connection conn = dbManager.getTestConnection();
				PreparedStatement stmt = conn.prepareStatement(SqlQueries.getStareAlertClient(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);) {

			stmt.setString(1, codSofer);
			stmt.setString(2, borderouActiv);
			stmt.setString(3, codClient);

			stmt.executeQuery();

			isSent = stmt.getResultSet().last();

		}

		return isSent;

	}

	private boolean sendSmsAlert(String codSofer, String borderouActiv, String codClient) throws SQLException {
		DBManager dbManager = new DBManager();

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		DateFormat timeFormat = new SimpleDateFormat("HHmmss");

		Calendar cal = Calendar.getInstance();

		boolean result = false;

		try (Connection conn = dbManager.getTestConnection(); PreparedStatement stmt = conn.prepareStatement(SqlQueries.sendAlertClient())) {

			stmt.setString(1, codSofer);
			stmt.setString(2, borderouActiv);
			stmt.setString(3, codClient);
			stmt.setString(4, dateFormat.format(cal.getTime()));
			stmt.setString(5, timeFormat.format(cal.getTime()));

			result = stmt.execute();

		}

		return result;

	}

}
