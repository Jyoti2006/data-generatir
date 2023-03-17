package com.ps.service.impl;

import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ps.model.OutputRecord;
import com.ps.services.GenerateWorkerData;
import com.ps.services.ReadJsonConfig;
import com.ps.utility.Constants;
import com.ps.utility.Utility;
import com.ps.xsd2java.ObjectFactory;
import com.ps.xsd2java.WorkerReference;

public class WorkerReferenceRecordGenerator {

	/**
	 * Generate each worker record
	 * 
	 * @return
	 */
	public OutputRecord createWorkerReferenceRecord() {
		ReadJsonConfig readJsonConfig = ReadJsonConfig.getInstance();
		ObjectMapper mapper = readJsonConfig.getObjectMapper();
		Map<String, List<Object>> dummyWorker = readJsonConfig.getDummWorker();
		readJsonConfig = null;
		final ObjectFactory factory = new ObjectFactory();
		OutputRecord output = new OutputRecord();
		WorkerReference workerRef = getWorkerRefDetails(factory, output, dummyWorker);

		String json = null;
		try {
			json = mapper.writeValueAsString(workerRef);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		json = json.replaceAll("\"", "\"\"");
		output.setLogicalJson(json);
		json = null;
		output.setSourceName((String) Utility.getRandomObjectFromList(dummyWorker.get(Constants.SOURCE_NAME)));
		output.setPosnId((String) Utility.getRandomObjectFromList(dummyWorker.get(Constants.POS_ID)));
		output.setCcid(workerRef.getJobDetails().get(0) != null
				? workerRef.getJobDetails().get(0).getJobOrganizationDetails().getCostCenterOrganizationIdentifier()
				: null);

		output.setLocId(
				workerRef.getJobDetails().get(0) != null ? workerRef.getJobDetails().get(0).getJobLocationIdentifier()
						: null);
		output.setJobPrfId(workerRef.getJobDetails().get(0) != null
				? workerRef.getJobDetails().get(0).getEmploymentJobProfileDetails().getJobProfileIdentifier()
				: null);

		output.setActiveStatus(workerRef.getJobDetails().get(0) != null
				? String.valueOf(workerRef.getJobDetails().get(0).isJobActiveFlag())
				: null);

		output.setCoId(workerRef.getJobDetails().get(0) != null
				? workerRef.getJobDetails().get(0).getJobOrganizationDetails().getCompanyOrganizationIdentifier()
				: null);
		output.setEmploymentTypeCode(workerRef.getJobDetails().get(0) != null
				? workerRef.getJobDetails().get(0).getPSWorkerEmploymentTypeCode()
				: null);

		return output;
	}

	private WorkerReference getWorkerRefDetails(ObjectFactory factory, OutputRecord output,
			Map<String, List<Object>> dummyWorker) {
		WorkerReference workerRef = factory.createWorkerReference();
		int randomWorkerIdLen = (int) Math.floor(Math.random() * (2) + 7);
		String entityId = Utility.generateRandomAplhaNumeric(randomWorkerIdLen, Constants.NUMERIC);
		output.setEntityId(entityId);
		workerRef.setWorkerIdentifier(entityId);
		workerRef.setExplicitPopulationInclusionCode(
				(String) Utility.getRandomObjectFromList(dummyWorker.get(Constants.POPULATION_CODE)));
		String effectiveDate = Utility.getRandomDate(2017, 2023);
		try {
			XMLGregorianCalendar t = DatatypeFactory.newInstance().newXMLGregorianCalendar(effectiveDate);
			workerRef.setEffectiveDate(t);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}

		Map<String, String> addressMap = ReadJsonConfig.getInstance().getAddressComponent();

		getWorkerPersonDetails(factory, workerRef, addressMap);

		getWorkerNameDetails(factory, workerRef, addressMap);

		getAddressDetails(factory, workerRef, addressMap);

		getWorkerEmailContactDetails(factory, workerRef, addressMap);

		getPersonIdentificationDetails(factory, workerRef, addressMap);

		getWorkerEmploymentSummary(factory, workerRef, addressMap);

		getJobDetails(factory, workerRef, addressMap);

		output.setId(Utility.generateRandomAplhaNumeric((int) Math.floor(Math.random() * (2) + 7), Constants.NUMERIC));

		output.setEmailId(workerRef.getWorkerEmailContactDetails().get(0).getEmailAddress());
		return workerRef;

	}

	private void getPersonIdentificationDetails(ObjectFactory factory, WorkerReference workerRef,
			Map<String, String> addressMap) {
		GenerateWorkerData identificationDetails = new WorkerPersonIdentificationGenerator();
		identificationDetails.populateData(factory, workerRef, addressMap);

	}

	private void getAddressDetails(ObjectFactory factory, WorkerReference workerRef, Map<String, String> addressMap) {
		GenerateWorkerData addressDetails = new WorkerAddressDetailsGenerator();
		addressDetails.populateData(factory, workerRef, addressMap);
	}

	private void getWorkerNameDetails(ObjectFactory factory, WorkerReference workerRef,
			Map<String, String> addressMap) {
		GenerateWorkerData nameDetails = new WorkerNameDetailsGenerator();
		nameDetails.populateData(factory, workerRef, addressMap);

	}

	private void getWorkerEmploymentSummary(ObjectFactory factory, WorkerReference workerRef,
			Map<String, String> addressMap) {
		GenerateWorkerData empSummary = new WorkerEmpSummaryGenerator();
		empSummary.populateData(factory, workerRef, addressMap);
	}

	private void getWorkerEmailContactDetails(ObjectFactory factory, WorkerReference workerRef,
			Map<String, String> addressMap) {
		GenerateWorkerData emailDetails = new WorkerEmailContactDetailsGenerator();
		emailDetails.populateData(factory, workerRef, addressMap);
	}

	private void getWorkerPersonDetails(ObjectFactory factory, WorkerReference workerRef,
			Map<String, String> addressMap) {

		GenerateWorkerData personDetials = new WorkerPersonDetailsGenerator();
		personDetials.populateData(factory, workerRef, addressMap);
	}

	private void getJobDetails(ObjectFactory factory, WorkerReference workerRef, Map<String, String> addressMap) {

		GenerateWorkerData jobDetials = new WorkerJobDetailsGenerator();
		jobDetials.populateData(factory, workerRef, addressMap);
	}

}
