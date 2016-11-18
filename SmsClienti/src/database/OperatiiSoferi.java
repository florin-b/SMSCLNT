package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

public class OperatiiSoferi {

	public List<String> getListSoferi(String codFiliala) throws SQLException {

		DBManager dbManager = new DBManager();
		List<String> listSoferi = new ArrayList<>();

		String sqlString = " SELECT cod FROM soferi WHERE fili =?";

		try (Connection con = dbManager.getTestConnection(); PreparedStatement prep = con.prepareStatement(sqlString);) {
			prep.setString(1, codFiliala);
			prep.executeQuery();

			ResultSet rs = prep.getResultSet();

			while (rs.next()) {
				listSoferi.add(rs.getString(1));
			}
		}
		return listSoferi;
	}

}
