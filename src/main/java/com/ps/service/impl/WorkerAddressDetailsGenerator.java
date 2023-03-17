package com.ps.service.impl;

import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import com.ps.services.GenerateWorkerData;
import com.ps.utility.Constants;
import com.ps.utility.Utility;
import com.ps.xsd2java.AddressDetails;
import com.ps.xsd2java.ObjectFactory;
import com.ps.xsd2java.WorkerReference;

public class WorkerAddressDetailsGenerator implements GenerateWorkerData {

	private Map<String, String> addressMap;

	@Override
	public void populateData(ObjectFactory factory, WorkerReference workerRef, Map<String, String> addressMap) {
		this.addressMap = addressMap;
		AddressDetails address = getAddressDetails(factory);
		workerRef.getWorkerAddressDetails().add(address);

	}

	private AddressDetails getAddressDetails(ObjectFactory factory) {
		AddressDetails address = factory.createAddressDetails();
		address.setAddressIdentifier(Utility.getAddressIdentifier());
		address.setAddressTypeCode(
				(String) Utility.getRandomObjectFromList(dummyWorker.get(Constants.ADDRESS_TYPE_CODE)));
		try {
			address.setAddressEffectiveDate(
					DatatypeFactory.newInstance().newXMLGregorianCalendar(Utility.getRandomDate(1987, 2022)));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}

		address.setAddressLine1Text(addressMap.get(Constants.ADDRESS));
		address.setAddressLine1DescriptionText("Street");
		address.setCountryCode(addressMap.get(Constants.COUNTRY_CODE));
		address.setCityName(addressMap.get(Constants.CITY_NAME));
		address.setStateCode(addressMap.get(Constants.STATE_CODE));
		address.setAddressFormatTypeCode("Extended");
		address.setPostalCode(addressMap.get(Constants.POSTAL_CODE));
		address.setCountyName(addressMap.get(Constants.COUNTRY_NAME));
		address.setCountryRegionCode(addressMap.get(Constants.STATE_CODE));
		address.setStateCode(addressMap.get(Constants.STATE_CODE));
		address.setStateDescriptionText(addressMap.get(Constants.STATE_NAME));
		address.setPrimaryAddressFlag(true);
		address.setPublicAddressFlag(true);
		String[] addressArray = { address.getAddressLine1Text(), address.getPostalCode() + " " + address.getCityName(),
				address.getCountyName() };
		address.setFormattedAddressText(Utility.getFormattedString(addressArray, "&#xa;"));
		return address;
	}

}
