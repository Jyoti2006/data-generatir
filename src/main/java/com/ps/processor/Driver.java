package com.ps.processor;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ps.service.impl.GCSFileUploader;
import com.ps.service.impl.RecordGeneratorImpl;
import com.ps.services.FileUploader;
import com.ps.services.RecordGenerator;

@Component
public class Driver {
	public void startProcess() throws JsonProcessingException {
		long start = System.currentTimeMillis();
		RecordGenerator recordGenerator = new RecordGeneratorImpl();
		recordGenerator.genrateRecords();
		// recordGenerator.writeToFile();
		System.out.println("Time taken to generate file (in sec): " + (System.currentTimeMillis() - start) / 1000);

		// Upload Output to GCS

		start = System.currentTimeMillis();
		FileUploader gcsFileUploader = new GCSFileUploader();
		gcsFileUploader.uploadFile();
		System.out.println("Uploading file to GCS took (in sec): " + (System.currentTimeMillis() - start) / 1000);

	}

}
