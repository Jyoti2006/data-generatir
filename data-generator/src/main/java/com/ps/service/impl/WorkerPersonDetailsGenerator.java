package com.ps.service.impl;

import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import com.ps.services.GenerateWorkerData;
import com.ps.utility.Constants;
import com.ps.utility.Utility;
import com.ps.xsd2java.ObjectFactory;
import com.ps.xsd2java.WorkerCitizenshipDetails;
import com.ps.xsd2java.WorkerNationalityDetails;
import com.ps.xsd2java.WorkerPersonalDetails;
import com.ps.xsd2java.WorkerReference;

public class WorkerPersonDetailsGenerator implements GenerateWorkerData {
	private Map<String, String> addressMap;

	@Override
	public void populateData(ObjectFactory factory, WorkerReference workerRef, Map<String, String> addressMap) {
		this.addressMap = addressMap;
		WorkerPersonalDetails workerPersonDetails = factory.createWorkerPersonalDetails();

		workerPersonDetails
				.setGenderCode((String) Utility.getRandomObjectFromList(dummyWorker.get(Constants.GENDER_CODE)));
		String maritalStatus = (String) Utility.getRandomObjectFromList(dummyWorker.get(Constants.MARITAL_STATUS));
		workerPersonDetails.setMaritalStatusCode(maritalStatus);

		try {
			workerPersonDetails.setBirthDate(
					DatatypeFactory.newInstance().newXMLGregorianCalendar(Utility.getRandomDate(1987, 1995)));
			if ("MARRIED".equals(maritalStatus))
				workerPersonDetails.setMaritalStatusDate(
						DatatypeFactory.newInstance().newXMLGregorianCalendar(Utility.getRandomDate(2010, 2022)));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		workerPersonDetails.setMaritalStatusCode(maritalStatus);
		workerPersonDetails.setBirthCountryCode(addressMap.get(Constants.COUNTRY_CODE));
		workerPersonDetails.setBirthCityName(addressMap.get(Constants.CITY_NAME));

		WorkerCitizenshipDetails workerCitizenshipDetails = getWorkerCitizenShipDetails(factory,
				workerPersonDetails.getBirthCountryCode());
		workerPersonDetails.getWorkerCitizenshipDetails().add(workerCitizenshipDetails);
		WorkerNationalityDetails workerNationalityDetails = getWorkerNationalityDetails(factory,
				workerPersonDetails.getBirthCountryCode());
		workerPersonDetails.getWorkerNationalityDetails().add(workerNationalityDetails);

		workerRef.setWorkerPersonDetails(workerPersonDetails);

	}

	@SuppressWarnings("unchecked")
	private WorkerCitizenshipDetails getWorkerCitizenShipDetails(ObjectFactory factory, String countryCode) {
		WorkerCitizenshipDetails workerCitizenshipDetails = factory.createWorkerCitizenshipDetails();
		workerCitizenshipDetails.setCitizenshipCountryCode(countryCode);
		Map<String, List<Object>> citizenStatusCode = (Map<String, List<Object>>) Utility
				.getRandomObjectFromList(dummyWorker.get(Constants.CITIZENSHIP_STATUS_CODE));
		Object citizenCode = Utility.getRandomObjectFromMap(citizenStatusCode);
		String codeDesc = (String) Utility.getRandomObjectFromList(citizenStatusCode.get(citizenCode));
		workerCitizenshipDetails.setCitizenshipStatusCode((String) citizenCode);
		workerCitizenshipDetails.setCitizenshipStatusDescription(codeDesc);
		return workerCitizenshipDetails;
	}

	private WorkerNationalityDetails getWorkerNationalityDetails(ObjectFactory factory, String birthCountryCode) {
		WorkerNationalityDetails workerNationalityDetails = factory.createWorkerNationalityDetails();
		workerNationalityDetails.setNationalityCountryCode(birthCountryCode);
		workerNationalityDetails.setPrimaryNationalityFlag(true);
		return workerNationalityDetails;
	}

}
