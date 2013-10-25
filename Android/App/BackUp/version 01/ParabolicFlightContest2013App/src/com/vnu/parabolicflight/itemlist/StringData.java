package com.vnu.parabolicflight.itemlist;

public class StringData {
	private String time, data;

	public StringData(String data, String time) {
		this.data = data;
		this.time = time;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}
	
	public void setTime(String time) {
		this.time = time;
	}

	public String getTime() {
		return time;
	}
}
