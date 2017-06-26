package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.CoordonateGps;
import beans.StareMasina;
import queries.SqlQueries;
import utils.MailOperations;

public class OperatiiMasina {

	public StareMasina getStareMasina(String codBorderou) throws SQLException {

		DBManager dbManager = new DBManager();
		StareMasina stareMasina = new StareMasina();
		CoordonateGps pozitie = null;

		try (Connection conn = dbManager.getProdConnection(); PreparedStatement prep = conn.prepareStatement(SqlQueries.getPozitieMasina());) {

			prep.setString(1, codBorderou);
			prep.executeQuery();

			ResultSet rs = prep.getResultSet();

			if (rs.next()) {

				stareMasina.setKilometraj(rs.getInt("mileage"));

				pozitie = new CoordonateGps();
				pozitie.setLatitude(rs.getDouble("latitude"));
				pozitie.setLongitude(rs.getDouble("longitude"));

				stareMasina.setCoordonateGps(pozitie);
				stareMasina.setViteza(rs.getInt("speed"));

			}

		}

		return stareMasina;
	}

	public String getNrMasina(String codBorderou) {

		DBManager dbManager = new DBManager();
		String nrMasina = "";

		try (Connection conn = dbManager.getProdConnection(); PreparedStatement prep = conn.prepareStatement(SqlQueries.getNrMasina());) {

			prep.setString(1, codBorderou);
			prep.executeQuery();

			ResultSet rs = prep.getResultSet();

			if (rs.next()) {
				nrMasina = rs.getString("masina");
			}

		} catch (SQLException e) {
			MailOperations.sendMail(e.toString());
		}

		return nrMasina;

	}

}
