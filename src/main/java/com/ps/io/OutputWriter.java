package com.ps.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.ps.model.OutputRecord;
import com.ps.services.ReadProperties;

public class OutputWriter {
	private FileWriter writer = null;
	// static int fileNo = 0;

	private OutputWriter() {
		try {
			// fileNo++;

			String date = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
			ReadProperties properties = ReadProperties.getInstance();
			String fileName = properties.getValue("output_file_path") + "-" + date + "-"
					+ Thread.currentThread().getName() + ".csv";
			File file = new File(fileName);

			if (!file.exists()) {
				writer = new FileWriter(file, true);
				String[] header = { "ID", "ENTITY_ID", "SOURCE_NAME", "LOGICAL_JSON", "VALID_FROM", "VALID_TO",
						"CREATED", "TRANSACTION_TIME", "LAST_UPDATED", "CCID", "POSNID", "LOCID", "JOBPRFLID",
						"EMAILID", "ACTIVE_STATUS", "ST1_LAST_UPDATE_TIME", "COID", "ENTITY_ID_HASH",
						"EMPLOYMENT_TYPE_CODE", "LOGICAL_JSON_HASH", "LOGICAL_JSON_ZIP" };
				writer.write("\"" + String.join("\",\"", header) + "\"");
				writer.write("\n");
			} else
				writer = new FileWriter(file, true);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static OutputWriter getInstance() {
		OutputWriter instance = new OutputWriter();
		return instance;
	}

	public FileWriter createCsvOutput() {
		return writer;
	}

	public static ObjectMapper createCustomObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		AnnotationIntrospector aiJaxb = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
		AnnotationIntrospector aiJackson = new JacksonAnnotationIntrospector();
		mapper.setAnnotationIntrospector(AnnotationIntrospector.pair(aiJaxb, aiJackson));
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		return mapper;
	}

	public void writeToFile(List<OutputRecord> records) {
		try {
			for (OutputRecord record : records) {
				writer.write(record.toString());
				writer.write("\n");
				writer.flush();
			}
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Written by: " + Thread.currentThread().getName());
	}

}
