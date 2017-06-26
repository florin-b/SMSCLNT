package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.google.maps.model.DirectionsRoute;

import beans.Address;
import beans.BeanClient;
import beans.BeanCoordonateClient;
import beans.BeanDistantaClient;
import beans.Borderou;
import beans.CoordonateGps;
import beans.StareMasina;
import enums.EnumTipMasina;
import queries.SqlQueries;
import utils.DateTimeUtils;
import utils.MailOperations;
import utils.MapsUtils;

public class OperatiiBorderou {

	public Borderou getBorderouActiv(String codSofer) throws SQLException {

		Borderou borderou = new Borderou();
		String borderouActiv = null;

		DBManager dbManager = new DBManager();

		try (Connection conn = dbManager.getProdConnection();
				PreparedStatement stmt = conn.prepareStatement(SqlQueries.getStareBorderou(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			stmt.setString(1, codSofer);
			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();
			if (rs.next()) {
				borderouActiv = rs.getString(1);

				borderou.setNrBorderou(borderouActiv);
				setTipMasina(conn, borderou);
				setLivrareTL(conn, borderou);

				int minFromStart = DateTimeUtils.dateDiffInMinutes(DateTimeUtils.formatDateLocal(rs.getString("datastart")), DateTimeUtils.getCurrentDate());

				borderou.setMinuteFromStart(minFromStart);

			}

		}

		return borderou;
	}

	private void setTipMasina(Connection conn, Borderou borderou) throws SQLException {
		EnumTipMasina tipMasina = null;
		String strTipMasina = null;
		PreparedStatement stmt = conn.prepareStatement(SqlQueries.getTipMasina(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		stmt.setString(1, borderou.getNrBorderou());
		stmt.executeQuery();

		ResultSet rs = stmt.getResultSet();
		if (rs.next()) {
			strTipMasina = rs.getString(1);

			if (strTipMasina.contains("CF85"))
				tipMasina = EnumTipMasina.DAF_CF85;
			else if (strTipMasina.contains("FAR85"))
				tipMasina = EnumTipMasina.DAF_FAR85;
			else if (strTipMasina.contains("LF55"))
				tipMasina = EnumTipMasina.DAF_LF55;
			else if (strTipMasina.contains("35C"))
				tipMasina = EnumTipMasina.IVECO_35C;
			else if (strTipMasina.contains("50C"))
				tipMasina = EnumTipMasina.IVECO_50C;
			else if (strTipMasina.contains("65C"))
				tipMasina = EnumTipMasina.IVECO_65C;
			else
				tipMasina = EnumTipMasina.NEDEFINIT;

		} else
			tipMasina = EnumTipMasina.NEDEFINIT;

		borderou.setTipMasina(tipMasina);
		borderou.setFiliala(rs.getString(2));

		stmt.close();

	}

	private void setLivrareTL(Connection conn, Borderou borderou) throws SQLException {

		PreparedStatement stmt = conn.prepareStatement(SqlQueries.getJudeteBorderou(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		stmt.setString(1, borderou.getNrBorderou());
		stmt.executeQuery();

		ResultSet rs = stmt.getResultSet();
		while (rs.next()) {
			if (rs.getString(1).equals("36"))
				borderou.setLivrareTL(true);
		}

		stmt.close();

	}

	public Set<BeanClient> getClientiBorderouFromDB(String codBorderou) throws SQLException {

		DBManager dbManager = new DBManager();
		Set<BeanClient> listClienti = new LinkedHashSet<>();
		BeanClient client = null;

		try (Connection con = dbManager.getProdConnection(); PreparedStatement prep = con.prepareStatement(SqlQueries.getClientiBorderouFromDB());) {
			prep.setString(1, codBorderou);
			prep.executeQuery();
			ResultSet rs = prep.getResultSet();

			while (rs.next()) {
				client = new BeanClient();
				client.setCodClient(rs.getString("codclient"));
				client.setCodAdresa(rs.getString("codadresa"));
				client.setDistClPrecedent(rs.getInt("distclant"));
				client.setInitKm(rs.getInt("initkm"));
				client.setSmsEmis(rs.getString("smsclient").equals("-1") ? false : true);
				listClienti.add(client);
			}

		}

		return listClienti;
	}

	public Set<BeanClient> getClientiBorderouOnline(String codBorderou, StareMasina stareMasina) throws SQLException {
		DBManager dbManager = new DBManager();

		Set<BeanClient> listClienti = new LinkedHashSet<>();
		BeanClient client = null;
		Address address = null;

		try (Connection con = dbManager.getProdConnection(); PreparedStatement prep = con.prepareStatement(SqlQueries.getClientiBorderou());) {

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

				if (!rs.getString("latitudine").equals("0"))
					client.setCoordGps(rs.getString("latitudine") + "," + rs.getString("longitudine"));
				else
					client.setCoordGps(con, rs, address);

				client.setCodAdresa(rs.getString("adresa_client"));
				client.setInitKm(stareMasina.getKilometraj());
				client.setSmsEmis(rs.getString("smsclient").equals("-1") ? false : true);

				if (Double.valueOf(client.getCoordGps().split(",")[0]) > 0)
					listClienti.add(client);

			}

			if (!listClienti.isEmpty())
				calculeazaTraseuBorderou(stareMasina, listClienti, codBorderou);

		}

		return listClienti;
	}

	private void calculeazaTraseuBorderou(StareMasina stareMasina, Set<BeanClient> listClienti, String codBorderou) {
		DirectionsRoute[] bordRoute = null;
		try {
			bordRoute = MapsUtils.traseuBorderou(stareMasina, listClienti);
		} catch (Exception e) {
			MailOperations.sendMail(e.toString());
		}

		int i = 0;
		for (BeanClient client : listClienti) {
			client.setDistClPrecedent((int) bordRoute[0].legs[i].distance.inMeters / 1000);
			i++;
		}

		try {
			saveEtapeBorderou(listClienti, stareMasina, codBorderou);
		} catch (SQLException e) {
			MailOperations.sendMail(e.toString());
		}

	}

	private void saveEtapeBorderou(Set<BeanClient> listClienti, StareMasina stareMasina, String codBorderou) throws SQLException {

		DBManager dbManager = new DBManager();

		try (Connection conn = dbManager.getProdConnection(); PreparedStatement stmt = conn.prepareStatement(SqlQueries.saveEtapeBorderou())) {

			int pos = 1;
			for (BeanClient client : listClienti) {
				stmt.setString(1, codBorderou);
				stmt.setInt(2, pos);
				stmt.setString(3, client.getCodClient());
				stmt.setString(4, client.getCodAdresa());
				stmt.setDouble(5, client.getDistClPrecedent());
				stmt.setDouble(6, stareMasina.getKilometraj());

				stmt.execute();

				pos++;
			}

		}

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

	public List<BeanDistantaClient> getDistantaClientReal(CoordonateGps pozitieMasina, Set<BeanClient> listClienti) {

		List<BeanDistantaClient> listDistante = new ArrayList<>();

		for (BeanClient client : listClienti) {

			BeanDistantaClient distanta = new BeanDistantaClient();
			distanta.setCodClient(client.getCodClient());
			distanta.setCodAdresa(client.getCodAdresa());

			CoordonateGps coordClient = new CoordonateGps(Double.valueOf(client.getCoordGps().split(",")[0]),
					Double.valueOf(client.getCoordGps().split(",")[1]));

			try {

				distanta.setDistanta((int) MapsUtils.distanceStraightXtoY(pozitieMasina.getLatitude(), pozitieMasina.getLongitude(), coordClient.getLatitude(),
						coordClient.getLongitude(), "K"));

			} catch (Exception e) {
				MailOperations.sendMail(e.toString());
			}
			listDistante.add(distanta);

		}

		return listDistante;
	}

	public List<String> getBorderouriByDate() throws SQLException {

		DBManager dbManager = new DBManager();

		List<String> listAdrese = new ArrayList<String>();

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		try (Connection conn = dbManager.getProdConnection(); PreparedStatement stmt = conn.prepareStatement(SqlQueries.getBorderouriByDate())) {

			stmt.setString(1, sdf.format(new Date()));

			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				listAdrese.add(rs.getString("adresa_client"));

			}

		}

		return listAdrese;
	}

	public void saveCoordonateAdrese(List<String> listAdrese) throws Exception {

		DBManager dbManager = new DBManager();
		try (Connection conn = dbManager.getProdConnection(); PreparedStatement stmt = conn.prepareStatement(SqlQueries.getAddressById())) {

			for (String codAdresa : listAdrese) {

				stmt.setString(1, codAdresa);
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {

					Address address = new Address();

					address.setCity(rs.getString("city1"));
					address.setStreet(rs.getString("street"));
					address.setStrNumber(rs.getString("house_num1"));
					address.setSector(rs.getString("region"));

					CoordonateGps coordGps = null;

					coordGps = MapsUtils.geocodeAddress(address);

					new Client().saveCoordonateAdresa(conn, codAdresa, coordGps);

				}

			}

		}

	}

	public static int getNrOpriri(String nrBorderou) throws SQLException {

		int nrOpriri = 0;
		DBManager dbManager = new DBManager();

		try (Connection conn = dbManager.getProdConnection(); PreparedStatement stmt = conn.prepareStatement(SqlQueries.getNrOpriri())) {

			stmt.setString(1, nrBorderou);
			stmt.setString(2, nrBorderou);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				nrOpriri = rs.getInt("opriri");
				if (nrOpriri < 0)
					nrOpriri = 0;
			}

		}

		return nrOpriri;
	}

	public boolean permitNotificareSms(String borderou, String codClient) throws SQLException {
		boolean permiteNotificare = false;

		DBManager dbManager = new DBManager();

		try (Connection conn = dbManager.getProdConnection(); PreparedStatement stmt = conn.prepareStatement(SqlQueries.getClientAnterior())) {

			stmt.setString(1, borderou);
			stmt.setString(2, borderou);
			stmt.setString(3, codClient);

			ResultSet rs = stmt.executeQuery();
			String clientAnterior = null;
			while (rs.next()) {

				clientAnterior = rs.getString("client");

			}

			if (clientAnterior == null)
				permiteNotificare = true;
			else {
				PreparedStatement stmt1 = conn.prepareStatement(SqlQueries.isClientLivrat());

				rs = stmt.executeQuery();

				while (rs.next()) {
					permiteNotificare = true;

				}

				stmt1.close();
				stmt1 = null;

			}

		}

		return permiteNotificare;
	}

}
