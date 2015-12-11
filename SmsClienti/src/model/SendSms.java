package model;

import java.sql.SQLException;
import java.util.List;

import database.OperatiiSoferi;

public class SendSms {

	public void TestAlg() {

		try {
			List<String> sof = new OperatiiSoferi().getListSoferi("GL10");

			System.out.println(sof);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
