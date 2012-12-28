package com.wat.pz.wizualizacja.collection;

import java.security.Timestamp;
import java.util.Date;

public class Measurement implements Comparable<Measurement> {
	
	private double  value;
	private long  time;
	private Date date;
	private String sDate;
	
	
	
	public Measurement(int v,long t,Date d,String sd){
		
		this.value =v;
		this.time =t;
		this.date=d;
		this.sDate=sd;
		
	}
	
	
	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
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
