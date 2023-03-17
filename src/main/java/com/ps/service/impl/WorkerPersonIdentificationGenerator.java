package com.ps.service.impl;

import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import com.ps.services.GenerateWorkerData;
import com.ps.utility.Constants;
import com.ps.utility.Utility;
import com.ps.xsd2java.ObjectFactory;
import com.ps.xsd2java.PersonIdentificationDetails;
import com.ps.xsd2java.WorkerReference;

public class WorkerPersonIdentificationGenerator implements GenerateWorkerData {
	private Map<String, String> addressMap;

	@Override
	public void populateData(ObjectFactory factory, WorkerReference workerRef, Map<String, String> addressMap) {
		this.addressMap = addressMap;
		PersonIdentificationDetails personIdentificationDetails = getPersonIdentificationDetails(factory);
		workerRef.getWorkerIdentificationDetails().add(personIdentificationDetails);

	}

	private PersonIdentificationDetails getPersonIdentificationDetails(ObjectFactory factory) {
		PersonIdentificationDetails personIdentificationDetails = factory.createPersonIdentificationDetails();
		String identificationStr = Utility.generateRandomAplhaNumeric(12, Constants.ALPHANUMERIC).toUpperCase();
		personIdentificationDetails.setIdentificationDocumentIdentifier(identificationStr);
		personIdentificationDetails.setIdentificationDocumentTypeCode(
				(String) Utility.getRandomObjectFromList(dummyWorker.get("personIdentificationDocumentType")));
		personIdentificationDetails.setIdentificationDocumentCountryCode(addressMap.get(Constants.COUNTRY_CODE));
		personIdentificationDetails.setIdentificationDocumentPrimaryFlag(true);
		personIdentificationDetails.setIdentificationDocumentSubTypeCode(addressMap.get(Constants.COUNTRY_CODE) + "-"
				+ Utility.generateRandomAplhaNumeric(4, Constants.NUMERIC));
		try {

			personIdentificationDetails.setIdentificationDocumentIssuedDate(
					DatatypeFactory.newInstance().newXMLGregorianCalendar(Utility.getRandomDate(2017, 2023)));

		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}

		return personIdentificationDetails;
	}

}
