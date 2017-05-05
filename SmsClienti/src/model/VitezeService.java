package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import beans.VitezaMedie;
import database.DBManager;
import enums.EnumTipMasina;
import queries.SqlQueries;
import utils.DateTimeUtils;

public class VitezeService {

	public List<VitezaMedie> getVitezeMedii() {
		DBManager dbManager = new DBManager();
		List<VitezaMedie> listVM = new ArrayList<>();

		try (Connection conn = dbManager.getProdConnection(); PreparedStatement stmt = conn.prepareStatement(SqlQueries.getVitezeMedii())) {

			stmt.setString(1, DateTimeUtils.addDaysToDate(-7));
			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {

				VitezaMedie vm = new VitezaMedie();
				vm.setTipMasina(getTipMasina(rs.getString(1)));
				vm.setFiliala(rs.getString(2));
				vm.setViteza(rs.getDouble(3));

				listVM.add(vm);
			}

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

		return listVM;

	}

	private EnumTipMasina getTipMasina(String strTipMasina) {

		EnumTipMasina tipMasina;

		if (strTipMasina.contains("CF85"))
			tipMasina = EnumTipMasina.DAF_CF85;
		else if (strTipMasina.contains("FAR85"))
			tipMasina = EnumTipMasina.DAF_FAR85;
		else if (strTipMasina.contains("LF55"))
			tipMasina = EnumTipMasina.DAF_LF55;
		else if (strTipMasina.contains("35C"))
			tipMasina = EnumTipMasina.IVECO_35C;
		else if (strTipMasina.contains("50C"))
			tipMasina = EnumTipMasina.IVECO_50C;
		else if (strTipMasina.contains("65C"))
			tipMasina = EnumTipMasina.IVECO_65C;
		else
			tipMasina = EnumTipMasina.NEDEFINIT;

		return tipMasina;
	}

}
