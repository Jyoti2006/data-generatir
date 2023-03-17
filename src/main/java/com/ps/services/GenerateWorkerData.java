package com.ps.services;

import java.util.List;
import java.util.Map;

import com.ps.xsd2java.ObjectFactory;
import com.ps.xsd2java.WorkerReference;

public interface GenerateWorkerData {
	public Map<String, List<Object>> dummyWorker = ReadJsonConfig.getInstance().getDummWorker();

	public void populateData(ObjectFactory factory, WorkerReference workerRef, Map<String, String> addressMap);
}
