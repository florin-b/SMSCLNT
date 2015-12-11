package beans;

public class BeanClient {

	private String codClient;
	private String numeClient;
	private Address adresa;

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

	@Override
	public String toString() {
		return "BeanClient [codClient=" + codClient + ", numeClient=" + numeClient + ", adresa=" + adresa + "] \n";
	}

}
