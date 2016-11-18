package utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBUtils {

	public static void closeConnections(PreparedStatement stmt, ResultSet rs) {

		try {

			if (stmt != null)
				stmt.close();

			if (rs != null)
				rs.close();

		} catch (Exception ex) {

		}

	}

}
