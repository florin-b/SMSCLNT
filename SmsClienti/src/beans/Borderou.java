package beans;

import java.util.Date;

import enums.EnumTipMasina;

public class Borderou {

	private String nrBorderou;
	private EnumTipMasina tipMasina;
	private String filiala;
	private boolean isLivrareTL;
	private int minuteFromStart;

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

	public boolean isLivrareTL() {
		return isLivrareTL;
	}

	public void setLivrareTL(boolean isLivrareTL) {
		this.isLivrareTL = isLivrareTL;
	}

	public int getMinuteFromStart() {
		return minuteFromStart;
	}

	public void setMinuteFromStart(int minuteFromStart) {
		this.minuteFromStart = minuteFromStart;
	}

	@Override
	public String toString() {
		return "Borderou [nrBorderou=" + nrBorderou + ", tipMasina=" + tipMasina + ", filiala=" + filiala + ", isLivrareTL=" + isLivrareTL
				+ ", minuteFromStart=" + minuteFromStart + "]";
	}

}
