package com.ps.service.impl;

import java.util.Map;

import com.ps.services.GenerateWorkerData;
import com.ps.utility.Constants;
import com.ps.utility.Utility;
import com.ps.xsd2java.EmailContactDetails;
import com.ps.xsd2java.ObjectFactory;
import com.ps.xsd2java.WorkerReference;

public class WorkerEmailContactDetailsGenerator implements GenerateWorkerData {
	private Map<String, String> addressMap;

	@Override
	public void populateData(ObjectFactory factory, WorkerReference workerRef, Map<String, String> addressMap) {
		this.addressMap = addressMap;
		EmailContactDetails workerEmailContactDetails = getWorkerEmailContactDetails(factory);
		workerRef.getWorkerEmailContactDetails().add(workerEmailContactDetails);

	}

	private EmailContactDetails getWorkerEmailContactDetails(ObjectFactory factory) {
		EmailContactDetails emailContactDetails = factory.createEmailContactDetails();
		emailContactDetails.setEmailUsageCode(
				(String) Utility.getRandomObjectFromList(dummyWorker.get(Constants.EMAIL_USAGE_CODE)));
		emailContactDetails
				.setEmailAddress((String) Utility.getRandomObjectFromList(dummyWorker.get(Constants.EMAIL_ADDRESS)));
		emailContactDetails.setPrimaryContactFlag(
				(boolean) Utility.getRandomObjectFromList(dummyWorker.get(Constants.BOOLEAN_VALUE)));
		emailContactDetails.setPublicContactFlag(
				(boolean) Utility.getRandomObjectFromList(dummyWorker.get(Constants.BOOLEAN_VALUE)));
		return emailContactDetails;
	}

}
