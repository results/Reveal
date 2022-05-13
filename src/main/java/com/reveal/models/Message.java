package com.reveal.models;

public class Message {
	
	private String context;
	
	private String level;
	
	private String text;
	
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return "Message [context=" + context + ", level=" + level + ", text=" + text + "]";
	}

	

}
