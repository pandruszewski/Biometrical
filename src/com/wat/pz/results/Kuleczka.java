package com.wat.pz.results;

import java.awt.Color;
import java.awt.Graphics2D;

public class Kuleczka {
	
	private int x;
	private int y;
	private Color color= Color.blue;
	private int size=10;
	
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
	public Kuleczka(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public Color getColor() {
		return color;
	}
	public void paintKuleczka(Graphics2D g){
		g.setColor(color);
		g.fillOval(x-(int)(size/2), y-(int)(size/2), size, size);
	}
	public void setColor(Color c){
		this.color=c;
	}
	
	

}
