package beans;

public class Address {

	private String idAdress;
	private String street;
	private String strNr;
	private String sector;
	private String city;
	private String country = "Romania";

	public String getIdAdress() {
		return idAdress;
	}

	public void setIdAdress(String idAdress) {
		this.idAdress = idAdress;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStrNumber() {
		return strNr;
	}

	public void setStrNumber(String number) {
		this.strNr = number;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAdress == null) ? 0 : idAdress.hashCode());
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
		Address other = (Address) obj;
		if (idAdress == null) {
			if (other.idAdress != null)
				return false;
		} else if (!idAdress.equals(other.idAdress))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Address [idAdress=" + idAdress + ", street=" + street + ", number=" + strNr + ", sector=" + sector
				+ ", city=" + city + ", country=" + country + "]";
	}

}
