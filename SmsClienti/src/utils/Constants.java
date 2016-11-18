package utils;

public class Constants {

	public static String GOOGLE_MAPS_API_KEY = "AIzaSyCO29NKFuNalBKo4tXhsvHBWYGYT6aN7ZY";

	public static double getTimpStationareH(EnumTipMasina tipMasina) {

		switch (tipMasina) {
		case DAF_CF85:
			return 30/60;
		case DAF_FAR85:
			return 35/60;
		case DAF_LF55:
			return 20/60;
		case IVECO_35C:
		case IVECO_50C:
		case IVECO_65C:
			return 15/60;
		default:
			return 15/60;
		}

	}

	public static int getVitezaMedie_KM_H(EnumTipMasina tipMasina) {
		switch (tipMasina) {
		case DAF_CF85:
			return 40;
		case DAF_FAR85:
			return 40;
		case DAF_LF55:
			return 55;
		case IVECO_35C:
		case IVECO_50C:
		case IVECO_65C:
			return 60;
		default:
			return 45;
		}

	}

}
