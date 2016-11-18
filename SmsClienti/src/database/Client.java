package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import beans.BeanClient;
import beans.BeanCoordonateClient;
import beans.CoordonateGps;
import queries.SqlQueries;
import utils.MapsUtils;

public class Client {

	public List<BeanCoordonateClient> getCoordonateClienti(Set<BeanClient> listClienti) throws Exception {

		List<BeanCoordonateClient> listCoordonate = new ArrayList<>();

		for (BeanClient client : listClienti) {

			BeanCoordonateClient coordonata = new BeanCoordonateClient();
			coordonata.setCodClient(client.getCodClient());
			coordonata.setCoordonate(MapsUtils.geocodeAddress(client.getAdresa()));
			listCoordonate.add(coordonata);

		}

		return listCoordonate;
	}

	public void saveCoordonateAdresa(Connection con, String idAddress, CoordonateGps coords) throws SQLException {

		Connection conn = con;
		PreparedStatement prep = conn.prepareStatement(SqlQueries.addCoordAdresa());

		prep.setString(1, idAddress);
		prep.setString(2, String.valueOf(coords.getLatitude()));
		prep.setString(3, String.valueOf(coords.getLongitude()));

		prep.execute();

		if (prep != null)
			prep.close();

	}

	public BeanClient getCoordonateAdresa() {
		return null;
	}

}
