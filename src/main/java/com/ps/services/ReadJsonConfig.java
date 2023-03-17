package com.ps.services;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ps.io.OutputWriter;
import com.ps.utility.Constants;
import com.ps.utility.Utility;

public class ReadJsonConfig {

	private static ReadJsonConfig instance = null;
	private static Map<String, List<Object>> dummyWorker = null;
	private static ObjectMapper mapper = null;
	private Map<String, String> addressMap = null;
	private static ReadProperties properties = ReadProperties.getInstance();

	@SuppressWarnings("unchecked")
	private ReadJsonConfig() {
		dummyWorker = new HashMap<>();
		mapper = OutputWriter.createCustomObjectMapper();
		String workerFileConfig = properties.getValue("worker_mock_config");
		File fileObj = new File(workerFileConfig);

		try {
			dummyWorker = mapper.readValue(fileObj, Map.class);
		} catch (JsonParseException e1) {

			e1.printStackTrace();
		} catch (JsonMappingException e1) {

			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getAddressComponent() {
		addressMap = new HashMap<>();

		String[] workerFileConfig = properties.getValue("generate_record_for_countries").trim().split(",", -1);
		List<Object> countryObj = dummyWorker.get("country");
		Set<String> countrySet = new HashSet<>();
		Collections.addAll(countrySet, workerFileConfig);
		countrySet.removeAll(Arrays.asList("", null));
		@SuppressWarnings("rawtypes")
		List<Object> listOutput = countryObj.stream().filter(obj -> {
			if (countrySet.size() == 0 || countrySet.contains((String) ("all")))
				return true;
			Map map = null;
			if (obj instanceof Map)
				map = (Map) obj;
			return (countrySet.contains((String) ((Object) map.get(Constants.COUNTRY_CODE))));
		}).collect(Collectors.toList());

		Map<String, List<Object>> country = (Map<String, List<Object>>) Utility.getRandomObjectFromList(listOutput);

		Map<String, List<Object>> state = (Map<String, List<Object>>) Utility
				.getRandomObjectFromList(country.get("state"));

		Map<String, List<Object>> address = (Map<String, List<Object>>) Utility
				.getRandomObjectFromList(state.get(Constants.ADDRESS));

		if ((Object) country.get(Constants.COUNTRY_CODE) instanceof String) {
			addressMap.put(Constants.COUNTRY_CODE, (String) ((Object) country.get(Constants.COUNTRY_CODE)));
			addressMap.put(Constants.COUNTRY_NAME, (String) ((Object) country.get(Constants.COUNTRY_NAME)));
			addressMap.put(Constants.CURRENCY, (String) ((Object) country.get(Constants.CURRENCY)));

			addressMap.put(Constants.STATE_CODE, (String) ((Object) state.get(Constants.STATE_CODE)));
			addressMap.put(Constants.STATE_NAME, (String) ((Object) state.get(Constants.STATE_NAME)));

			addressMap.put(Constants.CITY_NAME, (String) ((Object) address.get(Constants.CITY_NAME)));
			addressMap.put(Constants.POSTAL_CODE, (String) ((Object) address.get(Constants.POSTAL_CODE)));
			addressMap.put(Constants.ADDRESS, (String) ((Object) address.get(Constants.ADDRESS)));
		}
		return addressMap;
	}

	public static ReadJsonConfig getInstance() {
		if (instance == null)
			instance = new ReadJsonConfig();

		return instance;
	}

	public List<Object> getValue(String key) {
		return ReadJsonConfig.dummyWorker.get(key);
	}

	public ObjectMapper getObjectMapper() {
		return ReadJsonConfig.mapper;
	}

	public Map<String, List<Object>> getDummWorker() {
		return ReadJsonConfig.dummyWorker;
	}

	/*
	 * public Map<String, String> getAddressData() { return
	 * ReadJsonConfig.addressMap; }
	 */
}