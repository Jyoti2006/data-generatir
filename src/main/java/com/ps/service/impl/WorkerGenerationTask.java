package com.ps.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.ps.io.OutputWriter;
import com.ps.model.OutputRecord;
import com.ps.utility.Constants;
import com.ps.utility.Utility;

@SuppressWarnings("rawtypes")
public class WorkerGenerationTask implements Callable {
	private int chunkSize;
	private List<OutputRecord> outputRecords = null;
	DateFormat sdf = new SimpleDateFormat("dd-MMM-yy HH.mm.ss.SSSSSSSSS aa");

	WorkerGenerationTask(int chunkSize) {
		this.chunkSize = chunkSize;
		this.outputRecords = new ArrayList<>();
	}

	@Override
	public Object call() throws Exception {
		System.out.println("Processing thread: " + Thread.currentThread().getName());
		for (int i = 0; i < chunkSize; i++) {
			WorkerReferenceRecordGenerator workerReferenceRecordGenerator = new WorkerReferenceRecordGenerator();
			OutputRecord record = workerReferenceRecordGenerator.createWorkerReferenceRecord();
			setOutputRecordValues(record);
			outputRecords.add(record);
		}
		System.out.println("Processing done by thread : " + Thread.currentThread().getName());
		writeToFile();
		outputRecords.clear();
		return 0;
	}

	private void setOutputRecordValues(OutputRecord record) {
		record.setCreated(sdf.format(new java.util.Date()));
		record.setValidFrom(Utility.getRandomDate(2017, 2023));
		record.setValidTo(Utility.getRandomDate(2017, 2023));
		record.setTransactionTime(sdf.format(new java.util.Date()));
		record.setLastUpdated(sdf.format(new java.util.Date()));
		record.setSt1LastUpdatedTime(sdf.format(new java.util.Date()));
		record.setEntitiyIdHash(String.valueOf(record.getEntityId().hashCode()));
		record.setLogicalJsonHash(Utility.generateRandomAplhaNumeric(15, Constants.ALPHANUMERIC));
		record.setLogicalJsonZip("(BLOB)");
	}

	private void writeToFile() {
		OutputWriter.getInstance().writeToFile(outputRecords);
	}

}
