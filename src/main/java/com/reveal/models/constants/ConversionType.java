package com.reveal.models.constants;

import java.util.HashMap;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ConversionType {

	PIXEL("pixel"),
	AUDIENCE("audience");
	
	private static HashMap<String, ConversionType> conversionTypeNameMap = new HashMap<>();
	
    static {
        for (ConversionType e : values()) {
        	conversionTypeNameMap.put(e.name, e);
        }
    }

	private final String name;

	private ConversionType(String name) {
    	this.name = name;
    }
    
	@JsonCreator
    public ConversionType forValue(String name) {
		return conversionTypeNameMap.get(name);	
    }
	
	@JsonValue
	public String toValue() {
		for (Entry<String, ConversionType> entry : conversionTypeNameMap.entrySet()) {
			if (entry.getValue() == this)
				return entry.getKey();
		}
		return null;
	}
    
    public String getName() {
		return name;
	}

}
