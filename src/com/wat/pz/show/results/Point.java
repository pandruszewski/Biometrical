package com.wat.pz.show.results;

public class Point implements Comparable<Point>{
	private int x;
	private int y;
	private int indeks;

	public Point(int x, int y, int indeks) {
		this.x = x;
		this.y = y;
		this.indeks = indeks;
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
