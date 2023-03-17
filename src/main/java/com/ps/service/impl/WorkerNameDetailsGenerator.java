package com.ps.service.impl;

import java.util.Map;

import com.github.javafaker.Faker;
import com.ps.services.GenerateWorkerData;
import com.ps.utility.Constants;
import com.ps.utility.Utility;
import com.ps.xsd2java.NameDetails;
import com.ps.xsd2java.ObjectFactory;
import com.ps.xsd2java.WorkerReference;

public class WorkerNameDetailsGenerator implements GenerateWorkerData {
	private Map<String, String> addressMap;

	@Override
	public void populateData(ObjectFactory factory, WorkerReference workerRef, Map<String, String> addressMap) {
		this.addressMap = addressMap;
		NameDetails workerNameDetails = getWorkerNameDetails(factory);
		workerRef.getWorkerNameDetails().add(workerNameDetails);

	}

	private NameDetails getWorkerNameDetails(ObjectFactory factory) {
		NameDetails workerNameDetails = factory.createNameDetails();
		Faker faker = new Faker();

		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();

		/*
		 * String firstName = (String)
		 * Utility.getRandomObjectFromList(dummyWorker.get(Constants.FIRST_NAME));
		 * String lastName = (String)
		 * Utility.getRandomObjectFromList(dummyWorker.get(Constants.LAST_NAME));
		 */
		workerNameDetails.setFirstName(firstName);
		workerNameDetails.setLastName(lastName);
		workerNameDetails
				.setNameTypeCode((String) Utility.getRandomObjectFromList(dummyWorker.get(Constants.NAME_TYPE_CODE)));
		workerNameDetails.setNameCountryCode(addressMap.get(Constants.COUNTRY_CODE));
		workerNameDetails.setFormattedName(Utility.getFormattedName(firstName, lastName));
		workerNameDetails.setReportingName(Utility.getReportingName(firstName, lastName));
		return workerNameDetails;
	}

}
