package com.wat.pz.results;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;


public class SelectionSquare {
	private Integer startX;
	private Integer startY;
	private Integer endX;
	private Integer endY;
	private Integer startXOnScreen;
	private Integer startYOnScreen;
	public Integer getStartXOnScreen() {
		return startXOnScreen;
	}

	public void setStartXOnScreen(Integer startXOnScreen) {
		this.startXOnScreen = startXOnScreen;
	}

	public Integer getStartYOnScreen() {
		return startYOnScreen;
	}

	public void setStartYOnScreen(Integer startYOnScreen) {
		this.startYOnScreen = startYOnScreen;
	}

	private int height;
	private int width;
	public int getStartX() {
		return startX;
	}

	public void paintSquare(Graphics2D g){
	
		
		
		float alpha = 0.35f;
		Color color = new Color(0, 0, 1, alpha);
		g.setPaint(color);
		
		if(startX!= null&&startY != null && endX != null && endY!= null){
		System.out.println("rysuje SQUARE");
		
		g.fillRect(startX, startY, width, height);
		}
	}

	public Integer getStartY() {
		return startY;
	}

	public void setStartY(Integer startY) {
		this.startY = startY;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Integer getEndX() {
		return endX;
	}

	public void setEndX(Integer endX) {
		this.endX = endX;
		if(this.endX != null) 
			setWidth(this.endX - startX);
	}

	public Integer getEndY() {
		return endY;
	}

	public void setEndY(Integer endY) {
		this.endY = endY;
		if(this.endY != null)
			setHeight(this.endY - startY);
	}

	public void setStartX(Integer startX) {
		this.startX = startX;
	}

	public Dimension getSize(){
		return new Dimension(getWidth(), getHeight());
	}
	
}
