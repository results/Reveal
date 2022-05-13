package com.reveal.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;

import com.reveal.models.DailyConversion;
import com.reveal.models.constants.ConversionType;
import com.reveal.models.constants.DeviceType;

/**
 * 		
 * 		Using data from the conversion endpoint, calculate the following: 
 * 		- Total conversions 
 * 		- Total conversions by device type (primary vs household) 
 * 		- Total conversions by conversion type (pixel vs audience) 
 * 		- Average daily conversions - Average daily conversions by device type (primary vs household) 
 * 		- Average daily conversions by conversion type (pixel vs audience)
 *
 */
public class ConversionsCalculations {
	
	/**
	 * Just for output purposes
	 * @param dailyConversion
	 * @return JSON representation of all conversion counts
	 */
	public static String displayAllCalculations(DailyConversion dailyConversion) {
		Map<String, Object> calculations = new LinkedHashMap<>();//linked to keep in order
		calculations.put("Total Conversion Count", totalConversionCounts(dailyConversion));
		calculations.put("Average Daily Conversion Count", dailyAverageConversionsCount(dailyConversion));
		for(ConversionType type : ConversionType.values()) {
			calculations.put("Total by conversionType ("+type.getName()+") Count", totalConversionsByConversionTypeCount(dailyConversion, type));
			calculations.put("Average by conversionType ("+type.getName()+") Count", dailyAverageConversionsByConversionTypeCount(dailyConversion, type));
		}
		for(DeviceType type : DeviceType.values()) {
			calculations.put("Total by deviceType ("+type.getName()+") Count", totalConversionsByDeviceTypeCount(dailyConversion, type));
			calculations.put("Average by deviceType ("+type.getName()+" Count)", dailyAverageConversionsByDeviceTypeCount(dailyConversion, type));
		}		
		return new JSONObject(calculations).toString(4);
	}
	
	public static double average(List<Integer> toAverage) {//would place in another util class
		if (toAverage == null || toAverage.size() == 0) {
			return 0;
		}
		BigDecimal sum = BigDecimal.ZERO;
		for (Integer val : toAverage) {
			sum = sum.add(new BigDecimal(val));// BigDecimal allows for better precision vs double due to # of operations
		}
		return sum.divide(new BigDecimal(toAverage.size()), RoundingMode.HALF_EVEN).doubleValue();// convert to double, loss doesn't matter here
	}

	//would reuse lists but would require 2x iteration
	public static int totalConversionCounts(DailyConversion dailyConversion) {
		if (dailyConversion == null || dailyConversion.getConverions() == null || dailyConversion.getConverions().size() == 0) {
			return -1;
		}
		return dailyConversion.getConverions().stream().map(c -> c.getMetrics().getConversionCount())
				.collect(Collectors.summingInt(Integer::intValue));
	}

	public static int totalConversionsByDeviceTypeCount(DailyConversion dailyConversion, DeviceType type) {
		if (dailyConversion == null || dailyConversion.getConverions() == null || dailyConversion.getConverions().size() == 0) {
			return -1;
		}
		return dailyConversion.getConverions().stream().filter(c -> c.getConversionDimensions().getDeviceType() == type)
				.map(c -> c.getMetrics().getConversionCount()).collect(Collectors.summingInt(Integer::intValue));
	}

	public static int totalConversionsByConversionTypeCount(DailyConversion dailyConversion, ConversionType type) {
		if (dailyConversion == null || dailyConversion.getConverions() == null || dailyConversion.getConverions().size() == 0) {
			return -1;
		}
		return dailyConversion.getConverions().stream()
				.filter(c -> c.getConversionDimensions().getConversionType() == type)
				.map(c -> c.getMetrics().getConversionCount()).collect(Collectors.summingInt(Integer::intValue));
	}

	public static double dailyAverageConversionsCount(DailyConversion dailyConversion) {
		if (dailyConversion == null || dailyConversion.getConverions() == null || dailyConversion.getConverions().size() == 0) {
			return -1;
		}
		return average(dailyConversion.getConverions().stream().map(c -> c.getMetrics().getConversionCount())
				.collect(Collectors.toList()));
	}

	public static double dailyAverageConversionsByDeviceTypeCount(DailyConversion dailyConversion, DeviceType type) {
		if (dailyConversion == null || dailyConversion.getConverions() == null || dailyConversion.getConverions().size() == 0) {
			return -1;
		}
		return average(dailyConversion.getConverions().stream()
				.filter(c -> c.getConversionDimensions().getDeviceType() == type)
				.map(c -> c.getMetrics().getConversionCount()).collect(Collectors.toList()));
	}

	public static double dailyAverageConversionsByConversionTypeCount(DailyConversion dailyConversion, ConversionType type) {
		if (dailyConversion == null || dailyConversion.getConverions() == null || dailyConversion.getConverions().size() == 0) {
			return -1;
		}
		return average(dailyConversion.getConverions().stream()
				.filter(c -> c.getConversionDimensions().getConversionType() == type)
				.map(c -> c.getMetrics().getConversionCount()).collect(Collectors.toList()));
	}

}
