package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.CoordonateGps;
import queries.SqlQueries;

public class OperatiiMasina {

	public CoordonateGps getPozitieMasina(String codBorderou) throws SQLException {

		DBManager dbManager = new DBManager();
		CoordonateGps pozitie = null;

		try (Connection conn = dbManager.getProdConnection();
				PreparedStatement prep = conn.prepareStatement(SqlQueries.getPozitieMasina());) {

			prep.setString(1, codBorderou);
			prep.executeQuery();

			ResultSet rs = prep.getResultSet();

			if (rs.next()) {
				pozitie = new CoordonateGps();
				pozitie.setLatitude(rs.getDouble("latitude"));
				pozitie.setLongitude(rs.getDouble("longitude"));
			}

		}

		return pozitie;
	}

}
