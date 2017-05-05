package queries;

public class SqlQueries {

	public static String getClientiBorderou() {

		StringBuilder str = new StringBuilder();

		str.append("select  a.cod_client, c.nume, a.adresa_client, b.city1,b.street,b.house_num1,b.region, ");
		str.append("nvl(d.latitudine,'0') latitudine, nvl(d.longitudine, '0') longitudine , a.adresa_client, nvl(z.codclient,'-1') smsclient from ");
		str.append("sapprd.zdocumentesms a, sapprd.adrc b, clienti c, sapprd.zcoordadrese d, sapprd.zsmsclienti z, ");
		str.append(" sapprd.zordinelivrari o where ");
		str.append("a.nr_bord =?  and d.idadresa(+) = a.adresa_client ");
		str.append("and b.client = '900' and c.cod = a.cod_client ");
		str.append("and b.addrnumber = a.adresa_client ");
		str.append(" and z.borderou(+)=a.nr_bord and z.codclient(+) = a.cod_client and z.codadresa(+)=a.adresa_client ");
		str.append(" and o.borderou(+) = a.nr_bord and o.client(+) = a.cod_client and o.codadresa(+) = a.adresa_client   ");
		str.append(" order by o.pozitie ");

		return str.toString();
	}

	public static String getClientiBorderouFromDB() {

		StringBuilder str = new StringBuilder();
		str.append(
				" select  a.codclient, a.codadresa, a.distclant, a.initkm, nvl(z.codclient,'-1') smsclient from sapprd.ztraseuborderou a, sapprd.zsmsclienti z where a.borderou=? ");
		str.append(" and z.borderou(+)=a.borderou and z.codclient(+) = a.codclient and z.codadresa(+)=a.codadresa ");
		str.append(" order by to_number(a.poz) ");
		return str.toString();
	}

	public static String getPozitieMasina() {
		StringBuilder str = new StringBuilder();

		str.append(" select latitude, longitude, mileage from gps_index where device_id = ");
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

	public static String getTipMasina() {

		StringBuilder str = new StringBuilder();
		str.append(" select k.user1, b.fili from sapprd.aufk k, borderouri b where mandt = '900' and b.numarb =? and b.masina = k.ktext ");

		return str.toString();

	}

	public static String getStareAlertClient() {

		StringBuilder str = new StringBuilder();

		str.append(" select 1 from sapprd.zsmsclienti where codsofer = ? and borderou = ? and codclient = ? and codadresa = ?");

		return str.toString();
	}

	public static String getTelefonSmsClient() {

		StringBuilder str = new StringBuilder();

		str.append("select distinct tel_number from sapprd.kna1 k, sapprd.adr2 a, sapprd.adrt t ");
		str.append("where k.mandt = '900' and k.kunnr =? ");
		str.append("and k.mandt = a.client ");
		str.append("and k.adrnr = a.addrnumber ");
		str.append("and a.client = t.client ");
		str.append("and a.addrnumber = t.addrnumber ");
		str.append("and a.consnumber = t.consnumber ");
		str.append("and t.comm_type  = 'TEL' ");
		str.append("and t.remark = 'LIVRARI' ");

		return str.toString();

	}

	public static String sendAlertClient() {

		StringBuilder str = new StringBuilder();
		str.append(
				" insert into sapprd.zsmsclienti (mandt, codsofer, borderou, codclient, dataexp, oraexp, codadresa, gps, fms) values ('900',?,?,?,?,?,?,?,?) ");
		return str.toString();
	}

	public static String addCoordAdresa() {
		StringBuilder str = new StringBuilder();
		str.append(" insert into sapprd.zcoordadrese (mandt, idadresa, latitudine, longitudine) values ('900',?,?,?)  ");
		return str.toString();

	}

	public static String getCoordAdresa() {
		StringBuilder str = new StringBuilder();
		str.append(" select latitudine, longitudine from sapprd.zcoordadrese where idadresa=? ");
		return str.toString();

	}

	public static String saveEtapeBorderou() {
		StringBuilder str = new StringBuilder();
		str.append(" insert into sapprd.ztraseuborderou (mandt, borderou, poz, codclient, codadresa, distclant, initkm ) values ('900',?,?,?,?,?,?)");
		return str.toString();
	}

	public static String getBorderouriByDate() {
		StringBuilder str = new StringBuilder();
		str.append(
				" select distinct b.adresa_client from borderouri a, sapprd.zdocumentesms b, sapprd.zcoordadrese z where trunc(a.data_e) =? and b.nr_bord = a.numarb ");
		str.append(" and z.idadresa(+) = b.adresa_client and nvl(z.idadresa,'-1') = '-1' ");

		return str.toString();
	}

	public static String getAddressById() {
		StringBuilder str = new StringBuilder();
		str.append(" select city1, street, house_num1, region from sapprd.adrc d where d.client = '900' and d.addrnumber=? ");

		return str.toString();
	}

	public static String getNrOpriri() {
		StringBuilder str = new StringBuilder();
		str.append(" select ");
		str.append(" (select count(borderou) from sapprd.zsmsclienti where borderou =? )");
		str.append(" - ");
		str.append(" (select count(document) from sapprd.zevenimentsofer where document =? and client != document) opriri from dual ");

		return str.toString();

	}

	public static String getVitezeMedii() {
		StringBuilder str = new StringBuilder();

		str.append(" select tipmasina, filiala, avg(vitezamedie) from sapprd.zvitezemedii ");
		str.append(" where datac >=? group by tipmasina, filiala ");

		return str.toString();
	}

}
