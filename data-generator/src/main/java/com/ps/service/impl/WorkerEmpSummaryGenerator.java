package com.ps.service.impl;

import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.ps.services.GenerateWorkerData;
import com.ps.utility.Constants;
import com.ps.utility.Utility;
import com.ps.xsd2java.ObjectFactory;
import com.ps.xsd2java.WorkerEmploymentSummary;
import com.ps.xsd2java.WorkerReference;

public class WorkerEmpSummaryGenerator implements GenerateWorkerData {
	private Map<String, String> addressMap;

	@Override
	public void populateData(ObjectFactory factory, WorkerReference workerRef, Map<String, String> addressMap) {
		this.addressMap = addressMap;
		WorkerEmploymentSummary workerEmploymentSummary = getWorkerEmploymentSummary(factory);
		workerRef.setWorkerEmploymentSummary(workerEmploymentSummary);

	}

	private WorkerEmploymentSummary getWorkerEmploymentSummary(ObjectFactory factory) {
		WorkerEmploymentSummary workerEmploymentSummary = factory.createWorkerEmploymentSummary();
		workerEmploymentSummary.setActiveFlag(true);
		workerEmploymentSummary.setWorkerRetiredFlag(false);
		workerEmploymentSummary.setWorkerTerminatedFlag(false);
		String effectiveDate = Utility.getRandomDate(2017, 2023);
		try {
			XMLGregorianCalendar t = DatatypeFactory.newInstance().newXMLGregorianCalendar(effectiveDate);
			workerEmploymentSummary.setOriginalHireDate(t);
			workerEmploymentSummary.setMostRecentHireDate(t);
			workerEmploymentSummary.setSeniorityDate(t);

		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		if (workerEmploymentSummary.isActiveFlag())
			workerEmploymentSummary.setWorkerStatusCode(Constants.ACTIVE);
		else
			workerEmploymentSummary.setWorkerStatusCode("INACTIVE");
		workerEmploymentSummary.setInternationalAssignmentFlag(
				(boolean) Utility.getRandomObjectFromList(dummyWorker.get(Constants.BOOLEAN_VALUE)));
		workerEmploymentSummary.setApplicantIdentifier(Utility.generateRandomAplhaNumeric(11, Constants.NUMERIC));
		return workerEmploymentSummary;
	}

}
