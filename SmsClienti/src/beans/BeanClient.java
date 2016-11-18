package beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Client;
import utils.MapsUtils;

public class BeanClient {

	private String codClient;
	private String numeClient;
	private Address adresa;
	private String coordGps;
	private String codAdresa;
	private int distClPrecedent;
	private int initKm;
	private boolean smsEmis;

	public String getCodClient() {
		return codClient;
	}

	public void setCodClient(String codClient) {
		this.codClient = codClient;
	}

	public String getNumeClient() {
		return numeClient;
	}

	public void setNumeClient(String numeClient) {
		this.numeClient = numeClient;
	}

	public Address getAdresa() {
		return adresa;
	}

	public void setAdresa(Address adresa) {
		this.adresa = adresa;
	}

	public int getInitKm() {
		return initKm;
	}

	public void setInitKm(int initKm) {
		this.initKm = initKm;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresa == null) ? 0 : adresa.hashCode());
		result = prime * result + ((codClient == null) ? 0 : codClient.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BeanClient other = (BeanClient) obj;
		if (adresa == null) {
			if (other.adresa != null)
				return false;
		} else if (!adresa.equals(other.adresa))
			return false;
		if (codClient == null) {
			if (other.codClient != null)
				return false;
		} else if (!codClient.equals(other.codClient))
			return false;
		return true;
	}

	public String getCoordGps() {
		return coordGps;
	}

	public void setCoordGps(Connection con, ResultSet rs, Address adr) {
		try {
			if (!rs.getString("latitudine").trim().equals("0"))
				this.coordGps = rs.getString("latitudine") + "," + rs.getString("longitudine");
			else {
				CoordonateGps coordGps = null;
				try {
					coordGps = MapsUtils.geocodeAddress(adr);
					new Client().saveCoordonateAdresa(con, adr.getIdAdress(), coordGps);
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.coordGps = String.valueOf(coordGps.getLatitude()) + "," + String.valueOf(coordGps.getLongitude());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void setCoordGps(String coordGps) {
		this.coordGps = coordGps;
	}

	public String getCodAdresa() {
		return codAdresa;
	}

	public void setCodAdresa(String codAdresa) {
		this.codAdresa = codAdresa;
	}

	public int getDistClPrecedent() {
		return distClPrecedent;
	}

	public void setDistClPrecedent(int distClPrecedent) {
		this.distClPrecedent = distClPrecedent;
	}

	public boolean isSmsEmis() {
		return smsEmis;
	}

	public void setSmsEmis(boolean smsEmis) {
		this.smsEmis = smsEmis;
	}

	@Override
	public String toString() {
		return "BeanClient [codClient=" + codClient + ", numeClient=" + numeClient + ", adresa=" + adresa + ", coordGps=" + coordGps + ", codAdresa="
				+ codAdresa + ", distClPrecedent=" + distClPrecedent + ", initKm=" + initKm + ", smsEmis=" + smsEmis + "]";
	}

}
