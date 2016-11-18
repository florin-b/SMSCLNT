package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import queries.SqlQueries;

public class Adresa {

	public String getCoordAdresaFromDB(Connection conn, String codAdresa) throws SQLException {

		String coordAdresa = "0,0";

		Connection con = conn;
		PreparedStatement prep = con.prepareStatement(SqlQueries.getCoordAdresa());

		prep.setString(1, codAdresa);
		prep.executeQuery();
		ResultSet rs = prep.getResultSet();

		while (rs.next()) {
			coordAdresa = rs.getString("latitudine") + "," + rs.getString("longitudine");
		}

		if (rs != null)
			rs.close();

		if (prep != null)
			prep.close();

		return coordAdresa;
	}

}
