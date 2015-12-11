package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import beans.Address;
import beans.BeanClient;
import beans.BeanCoordonateClient;
import beans.BeanDistantaClient;
import beans.CoordonateGps;
import queries.SqlQueries;
import utils.MapsUtils;

public class OperatiiBorderou {

	public String getBorderouActiv(String codSofer) throws SQLException {

		String borderouActiv = null;

		DBManager dbManager = new DBManager();

		try (Connection conn = dbManager.getProdConnection();
				PreparedStatement stmt = conn.prepareStatement(SqlQueries.getStareBorderou(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			stmt.setString(1, codSofer);
			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();
			if (rs.next()) {
				borderouActiv = rs.getString(1);
			}
		}

		return borderouActiv;
	}

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

	
	public Set<BeanClient> getClientiBorderou(String codBorderou) throws SQLException {
		DBManager dbManager = new DBManager();

		Set<BeanClient> listClienti = new HashSet<>();
		BeanClient client = null;
		Address address = null;

		try (Connection con = dbManager.getProdConnection();
				PreparedStatement prep = con.prepareStatement(SqlQueries.getClientiSofer());) {

			prep.setString(1, codBorderou);
			prep.executeQuery();
			ResultSet rs = prep.getResultSet();

			while (rs.next()) {
				client = new BeanClient();
				client.setCodClient(rs.getString("cod_client"));
				client.setNumeClient(rs.getString("nume"));

				address = new Address();
				address.setIdAdress(rs.getString("adresa_client"));
				address.setCity(rs.getString("city1"));
				address.setStreet(rs.getString("street"));
				address.setStrNumber(rs.getString("house_num1"));
				address.setSector(rs.getString("region"));
				client.setAdresa(address);

				listClienti.add(client);

			}

		}

		return listClienti;
	}
	
	
	public List<BeanDistantaClient> getDistantaClientStraight(CoordonateGps pozitieMasina, List<BeanCoordonateClient> coordonateClienti) {

		List<BeanDistantaClient> listDistante = new ArrayList<>();

		for (BeanCoordonateClient client : coordonateClienti) {

			BeanDistantaClient distanta = new BeanDistantaClient();
			distanta.setCodClient(client.getCodClient());
			distanta.setDistanta((int) MapsUtils.distanceStraightXtoY(pozitieMasina.getLatitude(), pozitieMasina.getLongitude(),
					client.getCoordonate().getLatitude(), client.getCoordonate().getLongitude(), "K"));
			listDistante.add(distanta);

		}

		return listDistante;
	}

	public List<BeanDistantaClient> getDistantaClientReal(CoordonateGps pozitieMasina, List<BeanCoordonateClient> coordonateClienti) {

		List<BeanDistantaClient> listDistante = new ArrayList<>();

		for (BeanCoordonateClient client : coordonateClienti) {

			BeanDistantaClient distanta = new BeanDistantaClient();
			distanta.setCodClient(client.getCodClient());
			try {
				distanta.setDistanta((int) MapsUtils.distanceRealXtoY(pozitieMasina.getLatitude(), pozitieMasina.getLongitude(),
						client.getCoordonate().getLatitude(), client.getCoordonate().getLongitude()));
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			listDistante.add(distanta);

		}

		return listDistante;
	}

}
