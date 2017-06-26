package utils;

import java.util.Arrays;
import java.util.List;

public class UtilsFormatting {

	public static void main(String[] args) {

		List<String> str = Arrays.asList("AG10", "GL10", "DJ10");

		System.out.println(formatList(str));

	}

	public static String formatList(List<String> strList) {

		StringBuilder replaced = new StringBuilder();
		replaced.append("(");

		for (String s : strList) {
			if (replaced.toString().equals("(")) {
				replaced.append("'");
				replaced.append(s);
				replaced.append("'");
			} else {
				replaced.append(",'");
				replaced.append(s);
				replaced.append("'");
			}

		}

		replaced.append(")");

		return replaced.toString();

	}

}
