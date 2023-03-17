package com.ps.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Utility {
	private static Random RANDOM = new Random();

	/**
	 * @param list
	 * @return random object from list
	 */
	public static Object getRandomObjectFromList(List<Object> list) {
		return list.get(RANDOM.nextInt(list.size()));
	}

	/**
	 * @param set
	 * @return random object from set
	 */
	public static Object getRandomObjectFromSet(Set<String> set) {
		return set.stream().skip(new Random().nextInt(set.size())).findFirst().orElse(null);

	}

	/**
	 * @param map
	 * @return random object from map
	 */
	public static Object getRandomObjectFromMap(Map<String, List<Object>> map) {
		List<Object> keysAsArray = new ArrayList<Object>(map.keySet());
		return getRandomObjectFromList(keysAsArray);

	}

	/**
	 * @param start
	 * @param end
	 * @return random date between years start and end
	 */
	@SuppressWarnings("static-access")
	public static String getRandomDate(int start, int end) {
		GregorianCalendar gc = new GregorianCalendar();
		int year = randBetween(start, end);
		gc.set(gc.YEAR, year);
		int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
		gc.set(gc.DAY_OF_YEAR, dayOfYear);
		String date = gc.get(gc.YEAR) + "-" + (gc.get(gc.MONTH) + 1) + "-" + gc.get(gc.DAY_OF_MONTH);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = null;
		try {
			Date parsedDate = formatter.parse(date);
			strDate = formatter.format(parsedDate);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return strDate;
	}

	/**
	 * @param start
	 * @param end
	 * @return random number between start and end
	 */
	public static int randBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

	public static String getFormattedName(String firstName, String lastName) {
		return firstName + " " + lastName;
	}

	public static String getReportingName(String firstName, String lastName) {
		return lastName + ", " + firstName;
	}

	/**
	 * @param strArray
	 * @param delimeter
	 * @return joined String from string array
	 */
	public static String getFormattedString(String[] strArray, String delimeter) {
		return String.join(delimeter, strArray);
	}

	public static String getAddressIdentifier() {
		return "ADDRESS_REFERENCE-" + RANDOM.nextInt(50000);
	}

	/**
	 * @param size
	 * @param type
	 * @return random String of size 'size' and type 'type'
	 */
	public static String generateRandomAplhaNumeric(int size, String type) {
		String alphaNumericString = null;
		if ("numeric".equalsIgnoreCase(type))
			alphaNumericString = "0123456789";
		else if ("alphanumeric".equalsIgnoreCase(type))

			alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		else if ("string".equalsIgnoreCase(type))

			alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		else
			alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789)*&^%$#@:;";

		StringBuilder sb = new StringBuilder(size);

		for (int i = 0; i < size; i++) {
			int index = (int) (alphaNumericString.length() * Math.random());

			sb.append(alphaNumericString.charAt(index));
		}
		return sb.toString();

	}
}
