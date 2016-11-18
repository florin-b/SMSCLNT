package beans;

public class StareMasina {
	private int kilometraj;
	private CoordonateGps coordonateGps;

	public int getKilometraj() {
		return kilometraj;
	}

	public void setKilometraj(int kilometraj) {
		this.kilometraj = kilometraj;
	}

	public CoordonateGps getCoordonateGps() {
		return coordonateGps;
	}

	public void setCoordonateGps(CoordonateGps coordonateGps) {
		this.coordonateGps = coordonateGps;
	}

	@Override
	public String toString() {
		return "StareMasina [kilometraj=" + kilometraj + ", coordonateGps=" + coordonateGps + "]";
	}

}
