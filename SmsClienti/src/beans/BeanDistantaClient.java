package beans;

public class BeanDistantaClient {

	public String codClient;
	public int distanta;

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

	@Override
	public String toString() {
		return "BeanDistantaClient [codClient=" + codClient + ", distanta=" + distanta + "]";
	}

}
