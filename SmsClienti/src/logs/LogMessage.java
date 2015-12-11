package logs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;

public class LogMessage {

	public static void writeErrorLog(String message) {
		File file = new File("ErrorLog.txt");

		Path path = Paths.get("");

		Path filePath = Paths.get(path.toAbsolutePath().toString(), file.toString());

		if (Files.exists(filePath)) {
			try {
				writeToFile(filePath, message);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {

			try {
				Files.createFile(filePath);
				writeToFile(filePath, message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private static void writeToFile(Path filePath, String message) throws IOException {

		try (PrintWriter writer = new PrintWriter(new FileWriter(filePath.toFile(), true));) {
			Date date = new Date();
			writer.println(new Timestamp(date.getTime()) + " : " + message);
			writer.flush();
		}

	}

	public static void writeDebugLog(String message) {
		File file = new File("DebugLog.txt");

		Path path = Paths.get("");

		Path filePath = Paths.get(path.toAbsolutePath().toString(), file.toString());

		if (Files.exists(filePath)) {
			try {
				writeToFile(filePath, message);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {

			try {
				Files.createFile(filePath);
				writeToFile(filePath, message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
