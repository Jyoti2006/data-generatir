package com.ps.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import com.ps.services.ReadProperties;

public class WokerDataOutputWriter {
	static int fileNo = 0;
	static int numLines = 0;

	public void write(int fileContentSize, String outputDir) {
		long start = System.currentTimeMillis();
		try {
			fileNo++;
			File dir = new File(outputDir);
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());

			String[] fileNames = dir.list();
			PrintWriter pw = createFile(date);

			for (String fileName : fileNames) {
				File f = new File(dir, fileName);

				try (BufferedReader br = new BufferedReader(new FileReader(f))) {
					String line = br.readLine();
					while (line != null) {

						if (numLines == fileContentSize) {
							numLines = 0;
							fileNo++;
							pw.close();
							pw = createFile(date);
						}
						pw.println(line);
						line = br.readLine();
						numLines++;
					}
				}
				f.delete();
				pw.flush();
			}
			System.out.println("Reading from all files" + " in directory " + dir.getName() + " Completed");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Time taken to merge files: " + (System.currentTimeMillis() - start) / 1000);
	}

	private PrintWriter createFile(String date) {
		ReadProperties properties = ReadProperties.getInstance();
		String fileName = properties.getValue("output_file_path") + "-" + date + "-" + fileNo + ".csv";
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File(fileName));

			String[] header = { "ID", "ENTITY_ID", "SOURCE_NAME", "LOGICAL_JSON", "VALID_FROM", "VALID_TO", "CREATED",
					"TRANSACTION_TIME", "LAST_UPDATED", "CCID", "POSNID", "LOCID", "JOBPRFLID", "EMAILID",
					"ACTIVE_STATUS", "ST1_LAST_UPDATE_TIME", "COID", "ENTITY_ID_HASH", "EMPLOYMENT_TYPE_CODE",
					"LOGICAL_JSON_HASH", "LOGICAL_JSON_ZIP" };
			writer.write("\"" + String.join("\",\"", header) + "\"");
			writer.write("\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return writer;

	}
}
