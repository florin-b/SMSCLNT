package beans;

import utils.EnumTipMasina;

public class Borderou {

	private String nrBorderou;
	private EnumTipMasina tipMasina;

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

	@Override
	public String toString() {
		return "Borderou [nrBorderou=" + nrBorderou + ", tipMasina=" + tipMasina + "]";
	}

}
