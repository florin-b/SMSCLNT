package test;

import java.sql.SQLException;

import database.OperatiiBorderou;

public class TestClass {

	public static void main(String[] args) {

		boolean isLivrat = false;

		try {

			isLivrat = new OperatiiBorderou().permitNotificareSms("0001668945", "4119000023");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(isLivrat);

	}

}
