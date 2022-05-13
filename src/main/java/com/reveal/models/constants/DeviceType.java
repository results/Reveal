package com.reveal.models.constants;

import java.util.HashMap;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DeviceType {
	
	PRIMARY_DEVICE("primary_device"),
	HOUSEHOLD_DEVICE("household_device");
	
	private static HashMap<String, DeviceType> deviceTypeNameMap = new HashMap<>();
	
    static {
        for (DeviceType e : values()) {
        	deviceTypeNameMap.put(e.name, e);
        }
    }

	private final String name;

	private DeviceType(String name) {
    	this.name = name;
    }
    
	@JsonCreator
    public DeviceType forValue(String name) {
		return deviceTypeNameMap.get(name);	
    }
	
	@JsonValue
	public String toValue() {
		for (Entry<String, DeviceType> entry : deviceTypeNameMap.entrySet()) {
			if (entry.getValue() == this)
				return entry.getKey();
		}
		return null;
	}
    
    public String getName() {
		return name;
	}


}
