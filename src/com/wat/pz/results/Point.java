package com.wat.pz.results;



public class Point implements Comparable<Point>{
	private int x;
	private int y;
	private int indeks;
	private long time;
	private String date ;

	public long getTime() {
		return time;
	}

	public Point(int x, int y, int indeks,long time,String d) {
		this.x = x;
		this.y = y;
		this.indeks = indeks;
		this.time =time;
		this.date=d;
	}

	public String getDate() {
		return date;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getIndeks() {
		return indeks;
	}

	@Override
	public int compareTo(Point o1) {
		
		return this.y - o1.y;
	}

}
