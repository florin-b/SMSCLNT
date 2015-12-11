package beans;

public class BeanCoordonateClient {

	private String codClient;
	private CoordonateGps coordonate;

	public String getCodClient() {
		return codClient;
	}

	public void setCodClient(String codClient) {
		this.codClient = codClient;
	}

	public CoordonateGps getCoordonate() {
		return coordonate;
	}

	public void setCoordonate(CoordonateGps coordonate) {
		this.coordonate = coordonate;
	}

	@Override
	public String toString() {
		return "BeanCoordonateClient [codClient=" + codClient + ", coordonate=" + coordonate + "]";
	}

}
