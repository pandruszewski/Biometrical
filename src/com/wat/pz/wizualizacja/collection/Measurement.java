package com.wat.pz.wizualizacja.collection;

import java.security.Timestamp;

public class Measurement implements Comparable<Measurement> {
	
	private double  value;
	private long time;
	
	
	
	public Measurement(int v,long t){
		
		this.value =v;
		this.time =t;
		
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
	
	public double getValue() {
		return value;
	}


	@Override
	public int compareTo(Measurement o) {
		
		
		return (int) (this.value-o.value);
	}
	
	

}
