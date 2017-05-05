package beans;

import enums.EnumTipMasina;

public class Borderou {

	private String nrBorderou;
	private EnumTipMasina tipMasina;
	private String filiala;

	public String getNrBorderou() {
		return nrBorderou;
	}

	public void setNrBorderou(String nrBorderou) {
		this.nrBorderou = nrBorderou;
	}

	public EnumTipMasina getTipMasina() {
		return tipMasina;
	}

	public void setTipMasina(EnumTipMasina tipMasina) {
		this.tipMasina = tipMasina;
	}

	public String getFiliala() {
		return filiala;
	}

	public void setFiliala(String filiala) {
		this.filiala = filiala;
	}

	@Override
	public String toString() {
		return "Borderou [nrBorderou=" + nrBorderou + ", tipMasina=" + tipMasina + ", filiala=" + filiala + "]";
	}

}
