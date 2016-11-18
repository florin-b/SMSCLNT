package beans;

public class BeanDistantaClient {

	private String codClient;
	private int distanta;
	private String codAdresa;

	public String getCodClient() {
		return codClient;
	}

	public void setCodClient(String codClient) {
		this.codClient = codClient;
	}

	public int getDistanta() {
		return distanta;
	}

	public void setDistanta(int distanta) {
		this.distanta = distanta;
	}

	public String getCodAdresa() {
		return codAdresa;
	}

	public void setCodAdresa(String codAdresa) {
		this.codAdresa = codAdresa;
	}

	@Override
	public String toString() {
		return "BeanDistantaClient [codClient=" + codClient + ", distanta=" + distanta + ", codAdresa=" + codAdresa + "]";
	}

}
