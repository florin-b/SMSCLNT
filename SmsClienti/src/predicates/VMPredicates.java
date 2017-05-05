package predicates;

import java.util.function.Predicate;

import beans.Borderou;
import beans.VitezaMedie;

public class VMPredicates {

	public static Predicate<VitezaMedie> isTipMasinaFiliala(Borderou borderou) {
		return p -> p.getFiliala().equals(borderou.getFiliala()) && p.getTipMasina() == borderou.getTipMasina();
	}

}
