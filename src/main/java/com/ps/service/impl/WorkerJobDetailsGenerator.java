package com.ps.service.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

import com.ps.services.GenerateWorkerData;
import com.ps.utility.Constants;
import com.ps.utility.Utility;
import com.ps.xsd2java.BasePaymentPlanDetails;
import com.ps.xsd2java.CompensationDetails;
import com.ps.xsd2java.EmploymentJobProfileDetails;
import com.ps.xsd2java.JobDetails;
import com.ps.xsd2java.JobGovernanceRoleDetails;
import com.ps.xsd2java.JobOrganizationDetails;
import com.ps.xsd2java.ObjectFactory;
import com.ps.xsd2java.WorkerReference;

public class WorkerJobDetailsGenerator implements GenerateWorkerData {
	private Map<String, String> addressMap;

	@Override
	public void populateData(ObjectFactory factory, WorkerReference workerRef, Map<String, String> addressMap) {
		this.addressMap = addressMap;
		int noOfJobDetails = (int) Math.floor(Math.random() * (3) + 1);
		for (int i = 0; i < noOfJobDetails; i++) {
			JobDetails jobDetails = getJobDetails(factory,
					workerRef.getWorkerEmploymentSummary().getMostRecentHireDate(), workerRef.getWorkerIdentifier(), i);
			workerRef.getJobDetails().add(jobDetails);
		}

	}

	private JobDetails getJobDetails(ObjectFactory factory, XMLGregorianCalendar xmlGregorianCalendar, String entityId,
			int i) {
		JobDetails jobDetails = factory.createJobDetails();
		jobDetails.setJobIdentifier(entityId + Utility.generateRandomAplhaNumeric(3, Constants.NUMERIC));
		jobDetails.setPositionIdentifier(Utility.generateRandomAplhaNumeric(8, Constants.ALPHANUMERIC));
		jobDetails.setPSWorkerEmploymentTypeCode(
				(String) Utility.getRandomObjectFromList(dummyWorker.get(Constants.EMPLOYEE_CODE)));
		jobDetails.setPrimaryJobFlag(true);
		jobDetails.setMostRecentPrimaryJobFlag(
				(boolean) Utility.getRandomObjectFromList(dummyWorker.get(Constants.BOOLEAN_VALUE)));
		jobDetails.setLatestJobRecordFlag(
				(boolean) Utility.getRandomObjectFromList(dummyWorker.get(Constants.BOOLEAN_VALUE)));
		jobDetails
				.setBusinessTitle((String) Utility.getRandomObjectFromList(dummyWorker.get(Constants.BUSINESS_TITLE)));
		jobDetails.setPositionStartDate(xmlGregorianCalendar);
		jobDetails.setWorkerTypeCode(
				(String) Utility.getRandomObjectFromList(dummyWorker.get(Constants.WORKER_TYPE_CODE)));
		jobDetails.setWorkerTypeIdentifier(
				jobDetails.getWorkerTypeCode() + "^" + Utility.generateRandomAplhaNumeric(5, Constants.ALPHANUMERIC));
		jobDetails.setTimeTypeCode((String) Utility.getRandomObjectFromList(dummyWorker.get(Constants.TIME_CODE)));
		jobDetails.setScheduledWeeklyHours(
				Float.parseFloat((String) Utility.getRandomObjectFromList(dummyWorker.get(Constants.SCHEDULED_HOURS))));
		jobDetails.setJobLocationIdentifier(Utility.generateRandomAplhaNumeric(10, Constants.ALPHANUMERIC));
		jobDetails.setFullTimeEquivalent(1.0f);
		jobDetails.setExcludeFromHeadcountFlag(
				(boolean) Utility.getRandomObjectFromList(dummyWorker.get(Constants.BOOLEAN_VALUE)));
		jobDetails.setPayRateTypeCode(
				(String) Utility.getRandomObjectFromList(dummyWorker.get(Constants.PAY_RATE_TYPE_CODE)));
		jobDetails.setWorkShiftCode("WORK_SHIFT-" + Utility.generateRandomAplhaNumeric(5, Constants.NUMERIC));
		jobDetails.setWorkerStatusCode(Constants.ACTIVE);
		jobDetails.setJobActiveFlag(true);
		jobDetails.setEventClassificationCode(
				(String) Utility.getRandomObjectFromList(dummyWorker.get(Constants.EVENT_CLASSIFICATION_CODE)));
		jobDetails.setEventClassificationCategoryCode(
				(String) Utility.getRandomObjectFromList(dummyWorker.get(Constants.EVENT_CLASSIFICATION_CODE)));
		jobDetails.setEventClassificationSubcategoryCode(
				(String) Utility.getRandomObjectFromList(dummyWorker.get(Constants.EVENT_CLASSIFICATION_SUBTYPE_CODE)));
		jobDetails.setJobEmploymentRelationshipTypeCode(jobDetails.getPSWorkerEmploymentTypeCode());
		jobDetails.setJobEmploymentRelationshipSubTypeCode(
				(String) Utility.getRandomObjectFromList(dummyWorker.get(Constants.JOB_EMPLOYMENT_SUBTYPE_CODE)));
		jobDetails.setJobEffectiveDate(xmlGregorianCalendar);
		jobDetails.setJobEffectiveSequenceNumber(BigInteger.valueOf(i));
		jobDetails.setPSEffectiveSequenceNumber(BigInteger.valueOf(i));
		jobDetails.setPSHRStatusCode(
				(String) Utility.getRandomObjectFromList(dummyWorker.get(Constants.PS_HR_STATUS_CODE)));
		jobDetails.setPSBusinessUnitCode(
				addressMap.get(Constants.COUNTRY_CODE) + Utility.generateRandomAplhaNumeric(2, Constants.NUMERIC));
		jobDetails.setPSHireDate(xmlGregorianCalendar);
		jobDetails.setPSJobRecentStartDate(xmlGregorianCalendar);
		jobDetails.setPSSeniorityPayDate(xmlGregorianCalendar);
		jobDetails.setPSEmploymentInstanceHireDate(xmlGregorianCalendar);
		jobDetails.setPSActionCode((String) Utility.getRandomObjectFromList(dummyWorker.get(Constants.PS_ACTION_CODE)));
		jobDetails.setPSActionReasonCode(
				(String) Utility.getRandomObjectFromList(dummyWorker.get(Constants.PS_ACTION_REASON_CODE)));

		jobDetails.setPSActionDate(xmlGregorianCalendar);

		jobDetails.setGermanPayrollSeniorityCode(Utility.generateRandomAplhaNumeric(2, Constants.NUMERIC));
		jobDetails.setJobStartDate(xmlGregorianCalendar);
		jobDetails.setPSOrgInstanceOriginalHireDate(xmlGregorianCalendar);
		jobDetails.setPSOrgInstanceServiceDate(xmlGregorianCalendar);
		jobDetails.setPSJobIndicator(Utility.generateRandomAplhaNumeric(1, Constants.STRING));
		EmploymentJobProfileDetails employmentJobProfileDetails = getEmploymentJobProfileDetails(factory, dummyWorker);
		jobDetails.setEmploymentJobProfileDetails(employmentJobProfileDetails);
		JobOrganizationDetails jobOrganizationDetails = getJobOrganizationDetails(factory, dummyWorker,
				xmlGregorianCalendar);
		jobDetails.setJobOrganizationDetails(jobOrganizationDetails);

		JobGovernanceRoleDetails jobGovernanceRoleDetails = getJobGovernanceRoleDetails(factory, dummyWorker);
		jobDetails.setJobGovernanceRoleDetails(jobGovernanceRoleDetails);

		CompensationDetails compensationDetails = getCompensationDetails(factory, dummyWorker, xmlGregorianCalendar);
		jobDetails.setCompensationDetails(compensationDetails);

		return jobDetails;
	}

	private CompensationDetails getCompensationDetails(ObjectFactory factory, Map<String, List<Object>> dummyWorker,
			XMLGregorianCalendar xmlGregorianCalendar) {
		CompensationDetails compensationDetails = factory.createCompensationDetails();
		compensationDetails.setCompensationPackageIdentifier(
				(String) Utility.getRandomObjectFromList(dummyWorker.get("compensationPackageIdentifier")));
		compensationDetails.setCompensationGradeIdentifier(
				(String) Utility.getRandomObjectFromList(dummyWorker.get("compensationGradeIdentifier")));
		compensationDetails.setGradeSourceIdentifier(
				(String) Utility.getRandomObjectFromList(dummyWorker.get("gradeSourceIdentifier")));
		compensationDetails.setGradeName((String) Utility.getRandomObjectFromList(dummyWorker.get("gradeName")));
		compensationDetails.setCompensationGradeProfileIdentifier(
				(String) Utility.getRandomObjectFromList(dummyWorker.get("compensationGradeProfileIdentifier")));
		compensationDetails.setGradeProfileSourceIdentifier(
				(String) Utility.getRandomObjectFromList(dummyWorker.get("compensationGradeProfileIdentifier")));
		compensationDetails.setMinimumPayRange(
				Float.parseFloat((String) Utility.getRandomObjectFromList(dummyWorker.get("minimumPayRange"))));
		compensationDetails.setMaximumPayRange(
				Float.parseFloat((String) Utility.getRandomObjectFromList(dummyWorker.get("maximumPayRange"))));
		compensationDetails.setCompensationTotalBasePaymentAmount(1.0f);
		compensationDetails.setGradeProfileName(addressMap.get(Constants.COUNTRY_NAME));

		BasePaymentPlanDetails basePaymentPlanDetails = getBasePaymentPlanDetails(factory, dummyWorker,
				xmlGregorianCalendar);
		compensationDetails.getBasePaymentPlanDetails().add(basePaymentPlanDetails);

		return compensationDetails;
	}

	private BasePaymentPlanDetails getBasePaymentPlanDetails(ObjectFactory factory,
			Map<String, List<Object>> dummyWorker, XMLGregorianCalendar xmlGregorianCalendar) {
		BasePaymentPlanDetails basePaymentPlanDetails = factory.createBasePaymentPlanDetails();
		basePaymentPlanDetails.setBasePaymentPlanCode(
				(String) Utility.getRandomObjectFromList(dummyWorker.get("basePaymentPlanName")));
		basePaymentPlanDetails.setBasePaymentPlanName(
				(String) Utility.getRandomObjectFromList(dummyWorker.get("basePaymentPlanName")));
		basePaymentPlanDetails.setBasePaymentPlanEffectiveStartDate(xmlGregorianCalendar);

		basePaymentPlanDetails.setBasePaymentAnnualAmount(1.0f);
		basePaymentPlanDetails.setBasePayFullFTEAmount(1.0f);
		basePaymentPlanDetails.setBasePaymentPlanCurrencyCode(addressMap.get(Constants.CURRENCY));
		basePaymentPlanDetails.setBasePaymentPlanFrequencyCode(
				(String) Utility.getRandomObjectFromList(dummyWorker.get("basePaymentPlanFrequencyCode")));
		basePaymentPlanDetails.setApplyFullTimeEquivalentFlag(
				(boolean) Utility.getRandomObjectFromList(dummyWorker.get(Constants.BOOLEAN_VALUE)));

		return basePaymentPlanDetails;
	}

	private JobGovernanceRoleDetails getJobGovernanceRoleDetails(ObjectFactory factory,
			Map<String, List<Object>> dummyWorker) {
		int governanceIdentifierLen = (int) Math.floor(Math.random() * (2) + 7);
		JobGovernanceRoleDetails jobGovernanceRoleDetails = factory.createJobGovernanceRoleDetails();
		jobGovernanceRoleDetails.setDottedLineReportingWorkerIdentifier(
				Utility.generateRandomAplhaNumeric(governanceIdentifierLen, Constants.NUMERIC));
		jobGovernanceRoleDetails.setFunctionalManagerWorkerIdentifier(
				Utility.generateRandomAplhaNumeric(governanceIdentifierLen, Constants.NUMERIC));
		jobGovernanceRoleDetails.setInheritedManagerFlag(
				(boolean) Utility.getRandomObjectFromList(dummyWorker.get(Constants.BOOLEAN_VALUE)));
		return jobGovernanceRoleDetails;
	}

	private JobOrganizationDetails getJobOrganizationDetails(ObjectFactory factory,
			Map<String, List<Object>> dummyWorker, XMLGregorianCalendar xmlGregorianCalendar) {
		JobOrganizationDetails jobOrganizationDetails = factory.createJobOrganizationDetails();
		jobOrganizationDetails.setCompanyOrganizationIdentifier(
				(String) Utility.getRandomObjectFromList(dummyWorker.get("companyOrganizationIdentifier")));
		jobOrganizationDetails
				.setCostCenterOrganizationIdentifier(Utility.generateRandomAplhaNumeric(15, Constants.ALPHANUMERIC));
		jobOrganizationDetails.setAccessTypeOrganizationIdentifier(
				(String) Utility.getRandomObjectFromList(dummyWorker.get("accessTypeOrganizationIdentifier")));
		jobOrganizationDetails.setCostCenterOrganizationEntryDate(xmlGregorianCalendar);
		String payCode = addressMap.get(Constants.COUNTRY_CODE) + "_PAY_GRP_DEUTSCHE_BANK_AG_MONTHLY";
		jobOrganizationDetails.setPayGroupCode(payCode);
		jobOrganizationDetails.setPayGroupOrganizationIdentifier(payCode);

		return jobOrganizationDetails;
	}

	private EmploymentJobProfileDetails getEmploymentJobProfileDetails(ObjectFactory factory,
			Map<String, List<Object>> dummyWorker) {
		EmploymentJobProfileDetails employmentJobProfileDetails = factory.createEmploymentJobProfileDetails();
		employmentJobProfileDetails
				.setJobExemptFlag((boolean) Utility.getRandomObjectFromList(dummyWorker.get(Constants.BOOLEAN_VALUE)));
		employmentJobProfileDetails
				.setJobProfileIdentifier(Utility.generateRandomAplhaNumeric(8, Constants.ALPHANUMERIC));

		return employmentJobProfileDetails;
	}

}
