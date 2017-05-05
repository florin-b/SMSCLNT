package beans;

import enums.EnumTipMasina;

public class VitezaMedie {

	private String filiala;
	private EnumTipMasina tipMasina;
	private double viteza;

	public String getFiliala() {
		return filiala;
	}

	public void setFiliala(String filiala) {
		this.filiala = filiala;
	}

	public EnumTipMasina getTipMasina() {
		return tipMasina;
	}

	public void setTipMasina(EnumTipMasina tipMasina) {
		this.tipMasina = tipMasina;
	}

	public double getViteza() {
		return viteza;
	}

	public void setViteza(double viteza) {
		this.viteza = viteza;
	}

	@Override
	public String toString() {
		return "VitezaMedie [filiala=" + filiala + ", tipMasina=" + tipMasina + ", viteza=" + viteza + "]";
	}

}
