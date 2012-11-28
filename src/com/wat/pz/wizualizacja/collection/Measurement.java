package com.wat.pz.wizualizacja.collection;

import java.security.Timestamp;

public class Measurement {
	
	private int value;
	private long time;
	
	
	
	public Measurement(int v){
		
		this.value =v;
		this.time=System.currentTimeMillis();
	}
	
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public long getTime() {
		return time;
	}
	
	public int getValue() {
		return value;
	}
	
	

}
