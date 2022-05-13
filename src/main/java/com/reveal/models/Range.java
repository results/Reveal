package com.reveal.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Range {
	
	@JsonProperty("from")
	private Date startDate;
	
	@JsonProperty("to")
	private Date endDate;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "Range [startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
