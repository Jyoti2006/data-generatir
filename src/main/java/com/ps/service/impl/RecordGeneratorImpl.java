package com.ps.service.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.ps.io.WokerDataOutputWriter;
import com.ps.services.ReadProperties;
import com.ps.services.RecordGenerator;

public class RecordGeneratorImpl implements RecordGenerator {
	static FileWriter csvWriter = null;

	public void init() {
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void genrateRecords() {
		ReadProperties properties = ReadProperties.getInstance();
		int noOfRecords = Integer.parseInt(properties.getValue("number_of_records"));
		int chunkSize = Integer.parseInt(properties.getValue("chunk_size"));
		int numThreads = Integer.parseInt(properties.getValue("num_threads"));
		int chunks = (noOfRecords / chunkSize) + (noOfRecords % chunkSize > 0 ? 1 : 0);

		ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
		List<Future> futures = new ArrayList<>();
		for (int c = 0; c < chunks; c++) {
			if (c == chunks - 1 && (noOfRecords % chunkSize) > 0) {
				chunkSize = noOfRecords % chunkSize;
			}

			WorkerGenerationTask task = new WorkerGenerationTask(chunkSize);
			Future f = executorService.submit(task);
			futures.add(f);
		}

		executorService.shutdown();
		for (

		Future future : futures) {
			try {
				future.get();

			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

		// Check if all runnables are done (non-blocking)
		/*
		 * boolean allDone = true; for (Future<?> future : futures) { allDone &=
		 * future.isDone(); // check if future is done } if (allDone) close();
		 */
	}

	public void close() {
		if (csvWriter != null) {
			try {
				csvWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void writeToFile() {
		ReadProperties properties = ReadProperties.getInstance();
		int fileContentSize = Integer.parseInt(properties.getValue("file_content_size"));
		String outputDir=properties.getValue("output_dir");
		WokerDataOutputWriter wokerDataOutputWriter = new WokerDataOutputWriter();
		wokerDataOutputWriter.write(fileContentSize, outputDir);
	}

}
