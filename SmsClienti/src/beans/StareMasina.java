package beans;

import com.google.maps.model.LatLng;

public class StareMasina {
	private int kilometraj;
	private CoordonateGps coordonateGps;
	private int viteza;

	public int getKilometraj() {
		return kilometraj;
	}

	public void setKilometraj(int kilometraj) {
		this.kilometraj = kilometraj;
	}

	public CoordonateGps getCoordonateGps() {
		return coordonateGps;
	}

	public LatLng getLatLngCoords() {
		return new LatLng(coordonateGps.getLatitude(), coordonateGps.getLongitude());
	}

	public void setCoordonateGps(CoordonateGps coordonateGps) {
		this.coordonateGps = coordonateGps;
	}

	public int getViteza() {
		return viteza;
	}

	public void setViteza(int viteza) {
		this.viteza = viteza;
	}

	@Override
	public String toString() {
		return "StareMasina [kilometraj=" + kilometraj + ", coordonateGps=" + coordonateGps + ", viteza=" + viteza + "]";
	}

}
