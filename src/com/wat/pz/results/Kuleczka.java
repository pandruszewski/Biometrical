package com.wat.pz.results;

import java.awt.Color;
import java.awt.Graphics2D;

public class Kuleczka {
	
	private int x;
	private int y;
	private Color color= Color.blue;
	
	
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
	public void paintKuleczka(Graphics2D g){
		g.setColor(color);
		g.fillOval(x-2, y-2, 10, 10);
	}
	public void setColor(Color c){
		this.color=c;
	}
	
	

}
