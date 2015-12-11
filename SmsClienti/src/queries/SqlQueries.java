package queries;

public class SqlQueries {

	public static String getClientiSofer() {

		StringBuilder str = new StringBuilder();

		str.append("select a.cod_client, c.nume, a.adresa_client, b.city1,b.street,b.house_num1,b.region from ");
		str.append("sapprd.zdocumentesms a, sapprd.adrc b, clienti c where ");
		str.append("a.nr_bord =? ");
		str.append("and b.client = '900' and c.cod = a.cod_client ");
		str.append("and b.addrnumber = a.adresa_client ");

		return str.toString();
	}

	public static String getPozitieMasina() {
		StringBuilder str = new StringBuilder();

		str.append(" select latitude, longitude from gps_index where device_id = ");
		str.append(" (select id from gps_masini where nr_masina = ");
		str.append(" (select replace(masina,'-','') nrauto from ( ");
		str.append(" select masina from websap.borderouri where numarb =?))) ");

		return str.toString();
	}

	public static String getStareBorderou() {
		StringBuilder str = new StringBuilder();

		str.append(" select document from sapprd.zevenimentsofer where ");
		str.append(" document = (select numarb from ( ");
		str.append(" select numarb from websap.borderouri where sttrg in( 4, 6) ");
		str.append(" and cod_sofer =? order by sttrg desc,data_e asc) x where rownum<2) ");
		str.append(" and client = document and eveniment = 'P' ");

		return str.toString();
	}

	public static String getStareAlertClient() {

		StringBuilder str = new StringBuilder();

		str.append(" select 1 from sapprd.zsmsclienti where codsofer = ? and borderou = ? and codclient = ? ");

		return str.toString();
	}

	public static String sendAlertClient() {

		StringBuilder str = new StringBuilder();

		str.append(" insert into sapprd.zsmsclienti (mandt, codsofer, borderou, codclient, dataexp, oraexp) values ('900',?,?,?,?,?) ");

		return str.toString();
	}

}
